package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
 * OneOfInventoryItemDetailsPayloadItemType
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "itemType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Dairy.class, name = "Dairy"),
        @JsonSubTypes.Type(value = GrainsAndCereals.class, name = "GrainsAndCereals"),
        @JsonSubTypes.Type(value = Snacks.class, name = "Snacks"),
        @JsonSubTypes.Type(value = Beverages.class, name = "Beverages"),
        @JsonSubTypes.Type(value = CandyAndSweets.class, name = "CandyAndSweets"),
        @JsonSubTypes.Type(value = HouseHoldAndCleaning.class, name = "HouseHoldAndCleaning"),
        @JsonSubTypes.Type(value = PersonalCare.class, name = "PersonalCare"),
        @JsonSubTypes.Type(value = Others.class, name = "Others")
})
public interface OneOfInventoryItemDetailsPayloadItemType {

}
