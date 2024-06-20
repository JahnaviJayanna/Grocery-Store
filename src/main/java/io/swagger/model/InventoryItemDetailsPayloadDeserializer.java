package io.swagger.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDetailsPayloadDeserializer extends JsonDeserializer<InventoryItemDetailsPayload> {

    @Autowired
    private SmartValidator validator;

    @Autowired
    public InventoryItemDetailsPayloadDeserializer(SmartValidator validator) {
        this.validator = validator;
    }
    public InventoryItemDetailsPayloadDeserializer() {
    }

    @Override
    public InventoryItemDetailsPayload deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        ObjectNode rootNode = codec.readTree(p);


        // Create and return the InventoryItemDetailsPayload object
        InventoryItemDetailsPayload payload = new InventoryItemDetailsPayload();

        // Deserialize the categoryName
        JsonNode categoryNameNode = rootNode.get("categoryName");
        if (categoryNameNode!=null){
            InventoryItemDetailsPayload.CategoryNameEnum categoryName =
                    InventoryItemDetailsPayload.CategoryNameEnum.fromValue(categoryNameNode.asText());
            if(categoryName==null){
                try {
                    throw new ApiException(HttpStatus.BAD_REQUEST.value(), "Invalid value for category name");
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }else {
                payload.setCategoryName(categoryName);
            }
        }else {
            throw new NullPointerException("categoryName is missing in the payload");
        }


        // Deserialize the itemType based on the categoryName
        JsonNode itemTypeNode = rootNode.get("itemType");
        if (itemTypeNode!= null){
            OneOfInventoryItemDetailsPayloadItemType itemType = deserializeItemType(itemTypeNode, payload.getCategoryName());
            if(itemType==null) {
                try {
                    throw new IllegalArgumentException("Invalid itemType for category " + payload.getCategoryName());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                }
            }else {
                payload.setItemType(itemType);
            }
        }else {
            throw new NullPointerException("Item Type is missing in the payload");
        }

        // Deserialize the itemDetails
        JsonNode itemDetailsNode = rootNode.get("itemDetails");
        if (itemDetailsNode != null) {
            List<ItemDetails> itemDetailsList = new ArrayList<>();
            for (JsonNode itemNode : itemDetailsNode) {
                ItemDetails itemDetails = p.getCodec().treeToValue(itemNode, ItemDetails.class);
                // Validate each itemDetails
                BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(itemDetails, "itemDetails");
//                validator.validate(itemDetails, bindingResult);
                if (bindingResult.hasErrors()) {
                    try {
                        MethodParameter methodParameter = new MethodParameter(InventoryItemDetailsPayload.class.getMethod("setItemDetails", List.class), -1);
                        throw new MethodArgumentNotValidException(methodParameter,bindingResult);
                    } catch (MethodArgumentNotValidException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
                itemDetailsList.add(itemDetails);
            }
            payload.setItemDetails(itemDetailsList);
        }


        return payload;
    }

    private OneOfInventoryItemDetailsPayloadItemType deserializeItemType(JsonNode itemTypeNode,
                                                                         InventoryItemDetailsPayload.CategoryNameEnum categoryName) throws IOException {
        String itemTypeValue = itemTypeNode.asText();

        switch (categoryName) {
            case DAIRY:
                return Dairy.fromValue(itemTypeValue);
            case GRAINS_AND_CEREALS:
                return GrainsAndCereals.fromValue(itemTypeValue);
            case SNACKS:
                return Snacks.fromValue(itemTypeValue);
            case BEVERAGES:
                return Beverages.fromValue(itemTypeValue);
            case HOUSEHOLD_AND_CLEANING:
                return HouseHoldAndCleaning.fromValue(itemTypeValue);
            case PERSONAL_CARE:
                return PersonalCare.fromValue(itemTypeValue);
            case CANDY_AND_SWEETS:
                return CandyAndSweets.fromValue(itemTypeValue);
            case OTHERS:
                return Others.fromValue(itemTypeValue);
            default:
                throw new IllegalArgumentException("Invalid itemType for category " + categoryName);
        }
    }
}