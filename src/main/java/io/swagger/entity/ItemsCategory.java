package io.swagger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ITEMS_CATEGORIES")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemsCategory {

    @Id
    @Column(nullable = false, updatable = false, name = "CATEGORY_ID")
    private String categoryId = null;

    @Column(name = "CATEGORY_NAME", unique = true)
    @NotNull
    private String categoryName = null;

    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL)
    private List<Inventory> items;

    // Static variable to keep track of the counter
    private static int counter = 1;

    // Constructor to initialize categoryId with the generated value
    public ItemsCategory(String categoryName) {
        this.categoryId = "CAT" + String.format("%02d", counter++);
        this.categoryName = categoryName;
    }

}
