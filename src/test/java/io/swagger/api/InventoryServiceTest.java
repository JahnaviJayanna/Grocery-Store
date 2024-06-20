package io.swagger.api;

import io.swagger.entity.*;
import io.swagger.exception.*;
import io.swagger.model.*;
import io.swagger.repository.*;
import io.swagger.util.JwtUtil;
import io.swagger.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemCategoryRepository itemCategoryRepository;

    @Mock
    private ItemTypesRepository itemTypesRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private SalesInventoryRepository salesInventoryRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ResponseUtil responseUtil;
    private static final String INACTIVE = "INACTIVE";
    private static final String ACTIVE = "ACTIVE";

    private String userName = "testUser";

    private static final InventoryItemDetailsPayload.CategoryNameEnum category = InventoryItemDetailsPayload.CategoryNameEnum.DAIRY;
    private static final Dairy type = Dairy.CHEESE;

    private final String txnTimeStamp = "2024-05-30T12:00:00";

    private final String serviceRequestId = "abfae6a5-7320-4e3a-b3f2-93d2774fcce1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItemsToInventory_Success() {

        ItemsCategory categories = createCategory();
        ItemTypes itemType = createItemTypes();
        when(itemTypesRepository.findByItemTypeName(itemType.getItemTypeName())).thenReturn(itemType);
        when(inventoryRepository.findByItemName("Smartphone")).thenReturn(null);
        when(itemCategoryRepository.findByCategoryName(categories.getCategoryName())).thenReturn(categories);
        mockResponseUtil();

        AddUpdateInventorySuccess result = inventoryService.createItemsToInventory(createInventoryPayload(), "admin");


        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        assertNotNull(result.getItems());
        assertEquals("Successfully added items to inventory", result.getMessage());
        assertEquals(1, result.getItems().size());
        assertEquals("Smartphone", result.getItems().get(0).getItemName());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
    }

    @Test
    void createItemsToInventory_CategoryAndItemTypeMismatch() {
        // Mocking the repository methods
        when(itemCategoryRepository.findByCategoryName("Dairy")).thenReturn(createCategory());

        ItemTypes mismatchedTypes = new ItemTypes();
        mismatchedTypes.setItemTypeId("IT002");
        mismatchedTypes.setItemTypeName("Milk");
        mismatchedTypes.setCategoryId(new ItemsCategory());
        when(itemTypesRepository.findByItemTypeName("Cheese")).thenReturn(mismatchedTypes);

        // Calling the method to test and expecting an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.createItemsToInventory(createInventoryPayload(), "admin");
        });

        // Asserting the exception message
        assertEquals("Category and item type mismatch", exception.getMessage());
    }

    @Test
    void createItemsToInventory_DuplicateItem() {
        // Mocking the repository methods
        when(itemCategoryRepository.findByCategoryName("Dairy")).thenReturn(createCategory());
        when(itemTypesRepository.findByItemTypeName("Cheese")).thenReturn(createItemTypes());
        when(inventoryRepository.findByItemName("Smartphone")).thenReturn(createInventory());

        // Calling the method to test and expecting an exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.createItemsToInventory(createInventoryPayload(), "admin");
        });

        // Asserting the exception message
        assertEquals("Duplicate item with name: Smartphone", exception.getMessage());
    }

    @Test
    void deleteItem_Success() throws ApiException {
        Inventory inventory = createInventory();
        when(inventoryRepository.findById(inventory.getItemId())).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(inventory)).thenReturn(inventory);
        mockResponseUtil();

        DeleteInventorySuccess result = inventoryService.deleteItem(inventory.getItemId());

        //Then
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        assertNotNull(result);
        assertEquals("Successfully deleted item details", result.getMessage());
        assertEquals("ITM001",result.getItemId());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
    }


    @Test
    void deleteItem_ItemNotFound() {
        // Mocking the repository methods
        when(inventoryRepository.findById("ITEM_ID")).thenReturn(Optional.empty());

        // Calling the method to test and expecting an exception
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            inventoryService.deleteItem("ITEM_ID");
        });

        // Asserting the exception message
        assertEquals("Item not found with id: ITEM_ID", exception.getMessage());
    }

    @Test
    void deleteItem_ItemAlreadyInactive() {
        // Mocking the repository methods
        Inventory inventory = createInventory();
        inventory.setStatus(INACTIVE);
        when(inventoryRepository.findById(inventory.getItemId())).thenReturn(Optional.of(inventory));

        // Calling the method to test and expecting an exception
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            inventoryService.deleteItem(inventory.getItemId());
        });

        // Asserting the exception message
        assertEquals("Item not found with id: " + inventory.getItemId(), exception.getMessage());
    }

    @Test
    void deleteItem_SaveFailure() {

        Inventory inventory = createInventory();
        // Mocking the repository methods
        when(inventoryRepository.findById(inventory.getItemId())).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(inventory)).thenReturn(null);

        // Calling the method to test and expecting an exception
        ApplicationErrorException exception = assertThrows(ApplicationErrorException.class, () -> {
            inventoryService.deleteItem(inventory.getItemId());
        });

        // Asserting the exception message
        assertEquals("Unable to delete item", exception.getMessage());
    }

    @Test
    void fetchInventoryList_Success() throws ApiException {

        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(createInventory()));
        mockResponseUtil();

        GetInventoryItemsSuccess result = inventoryService.fetchInventoryList(null, null, null, null, "STAFF");

        assertNotNull(result.getInventoryList());
        assertEquals(1, result.getInventoryList().size());
        assertEquals("Dairy", result.getInventoryList().get(0).getCategoryName());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
    }

    @Test
    void testFetchInventoryList_ValidCategoryName() throws ApiException {
        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(createInventory()));
        when(itemCategoryRepository.findByCategoryName("Dairy")).thenReturn(new ItemsCategory());

        GetInventoryItemsSuccess response = inventoryService.fetchInventoryList(null, null, null, "Dairy", "STAFF");

        assertNotNull(response);
        assertEquals(1, response.getInventoryList().size());
        assertEquals("Dairy", response.getInventoryList().get(0).getCategoryName());
    }

    @Test
    void testFetchInventoryList_ValidItemType() throws ApiException {
        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(createInventory()));
        when(itemTypesRepository.findByItemTypeName("Cheese")).thenReturn(new ItemTypes());

        GetInventoryItemsSuccess response = inventoryService.fetchInventoryList(null, null, "Cheese", null, "STAFF");

        assertNotNull(response);
        assertEquals(1, response.getInventoryList().size());
        assertEquals("Cheese", response.getInventoryList().get(0).getCategoryList().get(0).getItemType());
    }

    @Test
    void testFetchInventoryList_NoParameters() throws ApiException {
        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(createInventory()));

        GetInventoryItemsSuccess response = inventoryService.fetchInventoryList(null, null, null, null, "STAFF");

        assertNotNull(response);
        assertEquals(1, response.getInventoryList().size());
    }

    @Test
    void fetchInventoryList_whenNoItemsPresent_shouldThrowNotFoundException() {
        when(inventoryRepository.findAll()).thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList(null, null, null, null, "USER"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("No items present", exception.getMessage());
    }

    @Test
    void fetchInventoryList_whenCategoryIsInvalid_shouldThrowNotFoundException() {
        when(itemCategoryRepository.findByCategoryName("invalidCategory")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList(null, null, null, "invalidCategory", "USER"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("CategoryName is invalid", exception.getMessage());
    }

    @Test
    void fetchInventoryList_whenItemTypeIsInvalid_shouldThrowNotFoundException() {
        when(itemTypesRepository.findByItemTypeName("invalidType")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList(null, null, "invalidType", null, "USER"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("itemType is invalid", exception.getMessage());
    }

    @Test
    void fetchInventoryList_whenItemNameIsInvalid_shouldThrowNotFoundException() {
        when(inventoryRepository.findByItemName("invalidName")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList(null, "invalidName", null, null, "USER"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("itemName is invalid", exception.getMessage());
    }

    @Test
    void fetchInventoryList_whenItemIdIsInvalid_shouldThrowNotFoundException() {
        when(inventoryRepository.findById("invalidId")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList("invalidId", null, null, null, "USER"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("itemId is invalid", exception.getMessage());
    }

    @Test
    void fetchInventoryList_validParametersInvalidCombination_NotFoundException() {

        Inventory inventory = createInventory();
        when(inventoryRepository.findAll()).thenReturn(Collections.singletonList(inventory));
        when(itemCategoryRepository.findByCategoryName(anyString())).thenReturn(createCategory());
        when(itemTypesRepository.findByItemTypeName(anyString())).thenReturn(createItemTypes());
        when(inventoryRepository.findByItemName(anyString())).thenReturn(inventory);
        when(inventoryRepository.findById(anyString())).thenReturn(Optional.of(inventory));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                inventoryService.fetchInventoryList("ITM002", "Smartphone", "Cheese", "Dairy", "STAFF"));

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("No items present with provided values", exception.getMessage());
    }

    @Test
    void updateItemsInInventory_Success() throws ApiException {
        Inventory inventory = createInventory();
        mockResponseUtil();
        when(inventoryRepository.findById(inventory.getItemId())).thenReturn(Optional.of(inventory));

        AddUpdateInventorySuccess result = inventoryService.updateItemsInInventory(inventory.getItemId(), createUpdatePayload(), "admin");

        assertEquals("ITM001", result.getItems().get(0).getItemId());
        assertEquals("Smartphone", result.getItems().get(0).getItemName());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    public void updateItemsInInventory_ItemNotFound() {
        Inventory inventory = createInventory();
        // Mock the repository to return empty for itemId
        when(inventoryRepository.findById(inventory.getItemId())).thenReturn(Optional.empty());

        // Verify the exception
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            inventoryService.updateItemsInInventory(inventory.getItemId(), createUpdatePayload(), userName);
        });

        assertEquals("Invalid itemId", exception.getMessage());
    }

    @Test
    public void updateItemsInInventory_ItemNameMismatch() {
        // Set a different item name in the existing inventory item
        Inventory inventoryItem = createInventory();
        inventoryItem.setItemName("differentItemName");
        when(inventoryRepository.findById(inventoryItem.getItemId())).thenReturn(Optional.of(inventoryItem));

        // Verify the exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            inventoryService.updateItemsInInventory(inventoryItem.getItemId(), createUpdatePayload(), userName);
        });

        assertEquals("Item name mismatch for provided itemId", exception.getMessage());
    }

    @Test
    void sellItems_Success() throws ApiException {

        when(inventoryRepository.findAllById(Collections.singleton("ITM001"))).thenReturn(Collections.singletonList(createInventory()));
        when(userRepository.findByUserName("admin")).thenReturn(new Users());
        mockResponseUtil();
        SellItemsSucess result = inventoryService.sellItems(createSellItemsPayload(), "admin");

        assertNotNull(result.getSalesDetails());
        assertEquals(1, result.getSalesDetails().getItemsList().size());
        assertEquals("Smartphone", result.getSalesDetails().getItemsList().get(0).getItemName());

        ArgumentCaptor<List<Inventory>> inventoryCaptor = ArgumentCaptor.forClass(List.class);
        verify(inventoryRepository, times(1)).saveAll(inventoryCaptor.capture());
        List<Inventory> capturedInventories = inventoryCaptor.getValue();
        assertNotNull(capturedInventories);
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
        verify(salesRepository, times(1)).save(any(Sales.class));
    }

    @Test
    void sellItems_MissingItemIds() {
        // Mocking the repository methods
        when(inventoryRepository.findAllById(anySet())).thenReturn(Collections.emptyList());

        // Calling the method to test and expecting an exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            inventoryService.sellItems(createSellItemsPayload(), "testUser");
        });

        // Asserting the exception message
        assertEquals("Invalid or missing item IDs: [ITM001]", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
    }

    @Test
    void sellItems_InsufficientQuantity() {
        // Mocking the repository methods
        Inventory inventoryItem = createInventory();
        inventoryItem.setUnit(3);
        when(inventoryRepository.findAllById(anySet())).thenReturn(Collections.singletonList(inventoryItem));

        // Calling the method to test and expecting an exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            inventoryService.sellItems(createSellItemsPayload(), "testUser");
        });

        // Asserting the exception message
        assertEquals("Insufficient quantity for item with ID: ITM001", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
    }

    @Test
    void sellItems_SaveFailure() {
        // Mocking the repository methods
        when(inventoryRepository.findAllById(anySet())).thenReturn(Collections.singletonList(createInventory()));
        when(userRepository.findByUserName("testUser")).thenReturn(createUser());
        when(salesRepository.save(any(Sales.class))).thenThrow(new RuntimeException("Save failed"));

        // Calling the method to test and expecting an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventoryService.sellItems(createSellItemsPayload(), "testUser");
        });

        // Asserting the exception message
        assertEquals("Save failed", exception.getMessage());
    }

    @Test
    void fetchSalesDetails_Success() throws ApiException {
        // Mocking the repository methods
        when(salesRepository.findByTxnId("12345")).thenReturn(Optional.of(createSalesPayload()));
        when(salesInventoryRepository.findByTxnId("12345")).thenReturn(createSalesInventory());

        // Calling the method to test
        SellItemsSucess result = inventoryService.fetchSalesDetails("12345");

        // Asserting the results
        assertNotNull(result);
        assertEquals("Fetch Sales Details successful", result.getMessage());
        assertEquals("12345", result.getTransactionId());
        assertCommonResponseParams(result.getTransactionTimeStamp(), result.getServiceRequestId(),  String.valueOf(result.getStatus()));
        SellItemsSucessSalesDetails salesDetails = result.getSalesDetails();
        assertNotNull(salesDetails);
        assertEquals(1, salesDetails.getItemsList().size());
        assertEquals(100.0f, salesDetails.getTotalAmount());
    }

    @Test
    void fetchSalesDetails_SalesIdNotFound() {
        // Mocking the repository method to return empty
        when(salesRepository.findByTxnId("12345")).thenReturn(Optional.empty());

        // Calling the method to test and expecting an exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            inventoryService.fetchSalesDetails("12345");
        });

        // Asserting the exception message
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode());
        assertEquals("Sales ID not found: 12345", exception.getMessage());
    }

    @Test
    void fetchSalesDetails_InvalidTxnId() {
        // Test with invalid input parameters
        assertThrows(ApiException.class, () -> inventoryService.fetchSalesDetails(""));
        assertThrows(ApiException.class, () -> inventoryService.fetchSalesDetails(null));
    }

    private Inventory createInventory(){
        Inventory inventory = new Inventory();
        inventory.setItemId("ITM001");
        inventory.setItemName("Smartphone");
        inventory.setCategoryId(createCategory());
        inventory.setItemTypeId(createItemTypes());
        inventory.setStatus(ACTIVE);
        inventory.setPrice(50.0f);
        inventory.setQuantity(5.0f);
        inventory.setUnit(10);

        return inventory;
    }

    private ItemsCategory createCategory(){
        ItemsCategory category = new ItemsCategory();
        category.setCategoryId("CAT001");
        category.setCategoryName("Dairy");
        return category;
    }

    private ItemTypes createItemTypes(){
        ItemTypes itemTypes = new ItemTypes();
        itemTypes.setItemTypeId("IT001");
        itemTypes.setItemTypeName("Cheese");
        itemTypes.setCategoryId(createCategory());
        return itemTypes;
    }

    private Users createUser() {
        Users user = new Users();
        user.setUserName("testUser");
        user.setUserRole("STAFF");
        user.setPassword("password");
        user.setStatus(ACTIVE);
        return user;
    }
    private InventoryItemDetailsPayload createInventoryPayload(){
        InventoryItemDetailsPayload payload = new InventoryItemDetailsPayload();
        payload.setCategoryName(category);
        payload.setItemType(type);
        ItemDetails itemDetails = createItemDetails();
        itemDetails.setDescription("Latest model");
        payload.setItemDetails(Collections.singletonList(itemDetails));
        return payload;
    }

    private UpdateInventoryItemsPayload createUpdatePayload(){
        UpdateInventoryItemsPayload payload = new UpdateInventoryItemsPayload();
        payload.setItemDetails(createItemDetails());
        return payload;
    }

    private ItemDetails createItemDetails(){
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItemName("Smartphone");
        itemDetails.setPrice(100.0f);
        itemDetails.setQuantity(10.0f);
        itemDetails.setUnits(2);
        return itemDetails;
    }

    private Sales createSalesPayload(){
        Sales sales = new Sales();
        sales.setTxnId("12345");
        sales.setServiceRequestId(serviceRequestId);
        sales.setTotalAmount(100.0f);
        sales.setTransactionTimestamp(txnTimeStamp);

        return sales;
    }

    private List<SalesInventory> createSalesInventory(){
        SalesInventory salesInventory = new SalesInventory();
        salesInventory.setSales(createSalesPayload());
        salesInventory.setUnitsSold(10);
        salesInventory.setInventory(new Inventory());

        List<SalesInventory> salesInventoryList = Arrays.asList(salesInventory);
        return salesInventoryList;
    }

    private SellItemsPayload createSellItemsPayload(){
        SellItemsPayload body = new SellItemsPayload();
        SellItemsPayloadItems item = new SellItemsPayloadItems();
        item.setItemId("ITM001");
        item.setUnits(5);
        body.setItems(Collections.singletonList(item));
        return body;
    }


    private void mockResponseUtil() {
        when(responseUtil.generateRequestId()).thenReturn(serviceRequestId);
        when(responseUtil.formatDateInGivenFormat(anyString())).thenReturn(txnTimeStamp);
    }

    private void assertCommonResponseParams(String timeStamp, String requestId, String status){
        assertEquals(txnTimeStamp, timeStamp);
        assertEquals(serviceRequestId, requestId);
        assertEquals("SUCCEEDED", status);
    }
}
