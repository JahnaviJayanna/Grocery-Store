package io.swagger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ITEM_TYPES")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemTypes {

    @Id
    @Column(nullable = false, updatable = false, name = "ITEM_TYPE_ID")
    private String itemTypeId = null;

    @Column(name = "ITEM_TYPE_NAME")
    private String itemTypeName = null;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private ItemsCategory categoryId = null;

    @OneToMany(mappedBy = "itemTypeId")
    private List<Inventory> items;

    // Static variable to keep track of the counter
    private static int counter = 1;

    // Constructor to initialize categoryId with the generated value
    public ItemTypes(String itemTypeName, ItemsCategory categoryId) {
        this.itemTypeId = "TYP" + String.format("%02d", counter++);
        this.itemTypeName = itemTypeName;
        this.categoryId= categoryId;
    }

}
