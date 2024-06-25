package io.swagger.api;

import io.swagger.entity.*;
import io.swagger.exception.*;
import io.swagger.model.*;
import io.swagger.repository.*;
import io.swagger.util.JwtUtil;
import io.swagger.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static io.swagger.api.Constants.*;

/**
 * Service class for managing inventory operations.
 */
@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private ItemTypesRepository itemTypesRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SalesInventoryRepository salesInventoryRepository;
    private JwtUtil jwtUtil;

    private ResponseUtil responseUtil;

    // Static variable to keep track of the counter
    private static int counter = 1;
    /**
     * Constructor for InventoryService.
     *
     * @param userRepository         the user repository
     * @param itemCategoryRepository the item category repository
     * @param itemTypesRepository    the item types repository
     * @param inventoryRepository    the inventory repository
     * @param salesRepository        the sales repository
     * @param salesInventoryRepository the sales inventory repository
     * @param jwtUtil                the JWT utility
     * @param responseUtil           the response utility
     */
    @Autowired
    public InventoryService(UserRepository userRepository, ItemCategoryRepository itemCategoryRepository, ItemTypesRepository itemTypesRepository, InventoryRepository inventoryRepository, SalesRepository salesRepository, SalesInventoryRepository salesInventoryRepository, JwtUtil jwtUtil, ResponseUtil responseUtil) {
        this.userRepository = userRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemTypesRepository = itemTypesRepository;
        this.inventoryRepository = inventoryRepository;
        this.salesRepository = salesRepository;
        this.salesInventoryRepository = salesInventoryRepository;
        this.jwtUtil = jwtUtil;
        this.responseUtil = responseUtil;
    }

    /**
     * Creates items in the inventory.
     *
     * @param body    the inventory item details payload
     * @param userName the user name
     * @return the success response of adding or updating inventory
     */
    @Transactional
    public AddUpdateInventorySuccess createItemsToInventory(InventoryItemDetailsPayload body, String userName) {
        try {
            logger.info("Creating items to inventory for user: {}", userName);

            List<Inventory> inventoryList = new ArrayList<>();
            ItemsCategory category = itemCategoryRepository.findByCategoryName(String.valueOf(body.getCategoryName()));
            ItemTypes types = itemTypesRepository.findByItemTypeName(String.valueOf(body.getItemType()));

            if (!category.getCategoryId().equals(types.getCategoryId().getCategoryId())) {
                throw new IllegalArgumentException("Category and item type mismatch");
            }

            for (ItemDetails item : body.getItemDetails()) {
                Inventory existingItem = inventoryRepository.findByItemName(item.getItemName());
                if (existingItem != null && existingItem.getItemTypeId().getItemTypeName().equals(types.getItemTypeName())) {
                    inventoryRepository.deleteAll(inventoryList);
                    counter-=inventoryList.size();
                    throw new IllegalArgumentException("Duplicate item with name: " + item.getItemName());
                }

                Inventory inventory = new Inventory();
                String timeStamp = responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss");
                inventory.setItemId("ITM" + String.format("%03d", counter++));
                inventory.setCategoryId(category);
                inventory.setItemTypeId(types);
                inventory.setItemName(item.getItemName());
                inventory.setDescription(item.getDescription());
                inventory.setPrice(item.getPrice());
                inventory.setQuantity(item.getQuantity());
                inventory.setUnit(item.getUnits());
                inventory.setCreatedBy(userName);
                inventory.setModifiedBy(userName);
                inventory.setCreatedOn(timeStamp);
                inventory.setModifiedOn(timeStamp);

                inventoryRepository.save(inventory);
                inventoryList.add(inventory);
            }

            List<ItemDetail> itemDetailList = inventoryList.stream()
                    .map(inventory -> {
                        ItemDetail itemDetail = new ItemDetail();
                        itemDetail.setItemId(inventory.getItemId());
                        itemDetail.setItemName(inventory.getItemName());
                        itemDetail.setCreatedOn(inventory.getCreatedOn());
                        itemDetail.setModifiedOn(inventory.getModifiedOn());
                        return itemDetail;
                    })
                    .collect(Collectors.toList());

            AddUpdateInventorySuccess response = new AddUpdateInventorySuccess();
            response.setMessage("Successfully added items to inventory");
            response.setStatus(SUCCEEDED);
            response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
            response.setServiceRequestId(responseUtil.generateRequestId());
            response.setItems(itemDetailList);

            logger.info("Items successfully created: {}", itemDetailList);
            return response;
        } catch (Exception ex) {
            logger.error("Error creating items to inventory", ex);
            throw ex;
        }
    }

    /**
     * Deletes an item from the inventory.
     *
     * @param itemId the item ID
     * @return the success response of deleting inventory
     * @throws ApiException if any error occurs
     */
    @Transactional
    public DeleteInventorySuccess deleteItem(
            String itemId)
            throws ApiException {

        Optional<Inventory> inventory = inventoryRepository.findById(itemId);
        inventory.orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Item not found with id: " + itemId));
        //Checking if user status is active
        if (inventory.get().getStatus().equals(INACTIVE)){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Item not found with id: " + itemId);
        }
        //Updating item status to inactive
        inventory.get().setStatus(INACTIVE);
        if(inventoryRepository.save(inventory.get()) == null){
            throw new ApplicationErrorException(HttpStatus.BAD_REQUEST.value(), "Unable to delete item");
        }
        DeleteInventorySuccess response = new DeleteInventorySuccess();
        response.setMessage("Successfully deleted item details");
        response.setServiceRequestId(responseUtil.generateRequestId());
        response.setStatus(SUCCEEDED);
        response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        response.setItemId(itemId);
        logger.info("Item deleted successfully: {}", itemId);
        return response;
    }

    /**
     * Fetches the inventory list based on provided filters.
     *
     * @param itemId      the item ID
     * @param itemName    the item name
     * @param itemType    the item type
     * @param categoryName the category name
     * @param workspace   the workspace
     * @return the success response of fetching inventory items
     * @throws ApiException if any error occurs
     */
    public GetInventoryItemsSuccess fetchInventoryList(
            String itemId,
            String itemName,
            String itemType,
            String categoryName,
            String workspace) throws ApiException {
        List<Inventory> inventoryList = inventoryRepository.findAll();

        //validation if db has values for provided query params
        if(categoryName!= null && itemCategoryRepository.findByCategoryName(categoryName)==null){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "CategoryName is invalid");
        }
        if (itemType != null && itemTypesRepository.findByItemTypeName(itemType)==null){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "itemType is invalid");
        }
        if (itemName!=null && inventoryRepository.findByItemName(itemName)==null){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "itemName is invalid");
        }
        if (itemId!=null && inventoryRepository.findById(itemId).isEmpty()){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "itemId is invalid");
        }


        if (!inventoryList.isEmpty()){
            // Filter the final list based on the provided parameters
            inventoryList = inventoryList.stream()
                    .distinct()
                    .filter(item -> (categoryName == null || categoryName.equals(item.getCategoryId().getCategoryName())) &&
                            (itemType == null || itemType.equals(item.getItemTypeId().getItemTypeName())) &&
                            (itemName == null || itemName.equals(item.getItemName()))&&
                            (itemId == null || itemId.equals(item.getItemId())))
                    .collect(Collectors.toList());
            if (inventoryList.isEmpty()){
                throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "No items present with provided values");
            }else {
                if (workspace.equals("STAFF")){
                    inventoryList = inventoryList.stream()
                            .filter(item ->(item.getStatus().equals("ACTIVE")))
                            .collect(Collectors.toList());
                }
                // Grouping and mapping the inventory list to the response format
                Map<String, Map<String, List<Inventory>>> groupedByCategoryAndType = inventoryList.stream()
                        .collect(Collectors.groupingBy(item -> item.getCategoryId().getCategoryName(),
                                Collectors.groupingBy(item -> item.getItemTypeId().getItemTypeName())));

                List<GetInventoryItemsSuccessInventoryList> inventorySuccessList = groupedByCategoryAndType.entrySet().stream()
                        .map(categoryEntry -> {
                            GetInventoryItemsSuccessInventoryList category = new GetInventoryItemsSuccessInventoryList();
                            category.setCategoryName(categoryEntry.getKey());

                            List<GetInventoryItemsSuccessCategoryList> itemTypes = categoryEntry.getValue().entrySet().stream()
                                    .map(itemTypeEntry -> {
                                        GetInventoryItemsSuccessCategoryList itemTypeObj = new GetInventoryItemsSuccessCategoryList();
                                        itemTypeObj.setItemType(itemTypeEntry.getKey());

                                        List<ItemDetailsWithId> items = itemTypeEntry.getValue().stream()
                                                .map(inventoryItem -> {
                                                    ItemDetailsWithId item = new ItemDetailsWithId();
                                                    item.setItemName(inventoryItem.getItemName());
                                                    item.setPrice(inventoryItem.getPrice());
                                                    item.setQuantity(inventoryItem.getQuantity());
                                                    item.setUnits(inventoryItem.getUnit());
                                                    item.setItemId(inventoryItem.getItemId());
                                                    return item;
                                                })
                                                .collect(Collectors.toList());

                                        itemTypeObj.setItemsList(items);
                                        return itemTypeObj;
                                    })
                                    .collect(Collectors.toList());

                            category.setCategoryList(itemTypes);
                            return category;
                        })
                        .collect(Collectors.toList());

                GetInventoryItemsSuccess response = new GetInventoryItemsSuccess();
                response.setMessage("Successfully deleted user details");
                response.setServiceRequestId(responseUtil.generateRequestId());
                response.setStatus(SUCCEEDED);
                response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
                response.setInventoryList(inventorySuccessList);
                logger.info("Item retrived successfully : {}", inventorySuccessList);
                return response;
            }
        }else {
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "No items present");
        }
    }

    /**
     * Updates items in the inventory based on the provided item ID.
     *
     * @param itemId   the ID of the inventory item to update
     * @param body     the payload containing updated item details
     * @param userName the username of the user performing the update
     * @return the success response after updating the item details
     * @throws ApiException if an error occurs during the update process
     */
    @Transactional
    public AddUpdateInventorySuccess updateItemsInInventory(String itemId,
                                                            UpdateInventoryItemsPayload body,
                                                            String userName) throws ApiException {
        // Fetch the inventory item by ID
        Inventory itemDetails = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Invalid itemId"));

        // Validate if the provided item name matches the existing item name
        if (!itemDetails.getItemName().equals(body.getItemDetails().getItemName())) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Item name mismatch for provided itemId");
        }

        // Update item details
        String timeStamp = responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss");
        itemDetails.setPrice(body.getItemDetails().getPrice());
        itemDetails.setQuantity(body.getItemDetails().getQuantity());
        itemDetails.setUnit(body.getItemDetails().getUnits());
        itemDetails.setStatus(ACTIVE);
        itemDetails.setModifiedBy(userName);
        itemDetails.setModifiedOn(timeStamp);
        inventoryRepository.save(itemDetails);

        // Construct item detail response
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItemId(itemDetails.getItemId());
        itemDetail.setItemName(itemDetails.getItemName());
        itemDetail.setCreatedOn(itemDetails.getCreatedOn());
        itemDetail.setModifiedOn(timeStamp);

        // Construct success response
        AddUpdateInventorySuccess response = new AddUpdateInventorySuccess();
        response.setMessage("Successfully updated item details for itemId: " + itemId);
        response.setStatus(SUCCEEDED);
        response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        response.setServiceRequestId(responseUtil.generateRequestId());
        response.setItems(Collections.singletonList(itemDetail));
        logger.info("Item updated successfully: {}", itemDetail);
        return response;
    }


    /**
     * Sells items from the inventory based on the provided payload.
     *
     * @param body     the payload containing items to sell
     * @param userName the username of the user performing the sale
     * @return the success response after selling the items
     * @throws ApiException if an error occurs during the selling process
     */
    @Transactional
    public SellItemsSucess sellItems(SellItemsPayload body, String userName) throws ApiException {

        // Fetch Inventory items
        Set<String> itemIds = body.getItems().stream()
                .map(SellItemsPayloadItems::getItemId)
                .collect(Collectors.toSet());
        List<Inventory> items = inventoryRepository.findAllById(itemIds);

        // Extract found item IDs
        Set<String> foundItemIds = items.stream()
                .map(Inventory::getItemId)
                .collect(Collectors.toSet());

        // Determine missing item IDs
        Set<String> missingItemIds = itemIds.stream()
                .filter(id -> !foundItemIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingItemIds.isEmpty()) {
            System.out.println("Invalid or missing item IDs: " + missingItemIds);
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid or missing item IDs: " + missingItemIds);
        }

        // Create a map of item ID to Inventory for quick lookup
        Map<String, Inventory> inventoryMap = items.stream()
                .collect(Collectors.toMap(Inventory::getItemId, item -> item));

        //Calculate the total amount and set payload for response
        float totalAmount = 0;
        List<ItemDetailsWithId> itemDetailsList = new ArrayList<>();
        for (SellItemsPayloadItems item : body.getItems()) {
            Inventory inventoryItem = inventoryMap.get(item.getItemId());
            totalAmount += item.getUnits() * inventoryItem.getPrice();

            // Create ItemDetail object
            ItemDetailsWithId itemDetail = new ItemDetailsWithId();
            itemDetail.setItemName(inventoryItem.getItemName());
            itemDetail.setPrice(inventoryItem.getPrice());
            itemDetail.setQuantity(inventoryItem.getQuantity());
            itemDetail.setUnits(item.getUnits());
            itemDetail.setItemId(item.getItemId());
            itemDetailsList.add(itemDetail);
        }

        // Group items by itemId and sum their units
        Map<String, Integer> itemIdToUnits = body.getItems().stream()
                .collect(Collectors.groupingBy(
                        SellItemsPayloadItems::getItemId,
                        Collectors.summingInt(SellItemsPayloadItems::getUnits)
                ));

        // Set the units for each item if efficient number of units available for item
        for (Inventory item : items) {
            if (item.getUnit() < itemIdToUnits.get(item.getItemId())) {
                throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Insufficient quantity for item with ID: " + item.getItemId());
            }
            item.setUnit(item.getUnit()-itemIdToUnits.get(item.getItemId()));
        }

        // Fetch User
        Users user = userRepository.findByUserName(userName);

        // Create Sales entity
        String salesId = "SL." + System.currentTimeMillis();
        String txnId = generateTransactionId();
        Sales sales = new Sales();
        sales.setSalesId(salesId);
        sales.setUserId(user);
        sales.setTotalAmount(totalAmount);
        sales.setServiceRequestId(responseUtil.generateRequestId());
        sales.setTransactionTimestamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        sales.setTxnId(txnId);

        // Create SalesInventory entities
        List<SalesInventory> salesInventories = new ArrayList<>();
        for (SellItemsPayloadItems item : body.getItems()) {
            Inventory inventoryItem = inventoryMap.get(item.getItemId());
            SalesInventory salesInventory = new SalesInventory();
            salesInventory.setSales(sales);
            salesInventory.setInventory(inventoryItem);
            salesInventory.setUnitsSold(item.getUnits());
            salesInventories.add(salesInventory);
        }
        sales.setSalesInventories(salesInventories);


        //Updating available units
        inventoryRepository.saveAll(items);
        //Adding sales records
        salesRepository.save(sales);

        // Create and populate the response object
        SellItemsSucessSalesDetails salesDetails = new SellItemsSucessSalesDetails();
        salesDetails.setItemsList(itemDetailsList);
        salesDetails.setTotalAmount(totalAmount);

        SellItemsSucess response = new SellItemsSucess();
        response.setServiceRequestId(responseUtil.generateRequestId());
        response.setTransactionTimeStamp(responseUtil.formatDateInGivenFormat("yyyy-MM-dd'T'HH:mm:ss"));
        response.setMessage("Sales successful");
        response.setStatus("SUCCEEDED");
        response.setTransactionId(txnId);
        response.setSalesDetails(salesDetails);

        logger.info("Items sold successfully", salesDetails);

        return response;
    }

    private String generateTransactionId() {
        // Fixed prefix
        String prefix = "IS";

        //Current date in "yyMMdd" format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String date = dateFormat.format(new Date());

        //Random 4-digit number
        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000;

        // Combine parts to form the transaction ID
        String transactionId = String.format("%s.%s.%04d", prefix, date, randomNum);
        return transactionId;
    }

    /**
     * Fetches sales details for a given transaction ID.
     *
     * @param txnId the transaction ID to fetch sales details
     * @return the success response containing sales details for the given transaction ID
     * @throws ApiException if the transaction ID is not found or an error occurs during fetching
     */
    public SellItemsSucess fetchSalesDetails(String txnId) throws ApiException {
        // Check if the Sales ID exists
        Optional<Sales> salesOptional = salesRepository.findByTxnId(txnId);
        if (!salesOptional.isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Sales ID not found: " + txnId);
        }

        // Fetch sales details
        List<SalesInventory> details = salesInventoryRepository.findByTxnId(txnId);
        System.out.println(details.get(0).getUnitsSold());
        System.out.println();

        // Construct the response
        List<ItemDetailsWithId> itemDetailsList = details.stream().map(detail -> {
            ItemDetailsWithId itemDetail = new ItemDetailsWithId();
            itemDetail.setItemId(detail.getInventory().getItemId());
            itemDetail.setItemName(detail.getInventory().getItemName());
            itemDetail.setPrice(detail.getInventory().getPrice());
            itemDetail.setQuantity(detail.getInventory().getQuantity());
            itemDetail.setUnits(detail.getUnitsSold());
            return itemDetail;
        }).collect(Collectors.toList());

        // Fetch sales entity to get the total amount
        Sales sales = salesOptional.get();

        // Create SellItemsSuccess response
        SellItemsSucessSalesDetails salesDetails = new SellItemsSucessSalesDetails();
        salesDetails.setItemsList(itemDetailsList);
        salesDetails.setTotalAmount(sales.getTotalAmount());

        SellItemsSucess response = new SellItemsSucess();
        response.setServiceRequestId(sales.getServiceRequestId());
        response.setTransactionTimeStamp(sales.getTransactionTimestamp());
        response.setMessage("Fetch Sales Details successful");
        response.setStatus("SUCCEEDED");
        response.setTransactionId(txnId);
        response.setSalesDetails(salesDetails);
        logger.info("Item retrived successfully for {}", txnId);
        return response;
    }
}
