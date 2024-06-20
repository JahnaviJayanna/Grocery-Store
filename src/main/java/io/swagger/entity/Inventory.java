package io.swagger.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "INVENTORY")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {

    @Id
    @Column(nullable = false, updatable = false, name = "ITEM_ID", length = 8)
    private String itemId = null;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    private ItemsCategory categoryId = null;

    @ManyToOne
    @JoinColumn(name = "ITEM_TYPE_ID", referencedColumnName = "ITEM_TYPE_ID")
    private ItemTypes itemTypeId = null;

    @Column(name = "ITEM_NAME", unique = true, updatable = false, nullable = false)
    @NotEmpty
    private String itemName = null;

    @Column(name = "DESCRIPTION")
    private String description = null;

    @Column(name = "PRICE", nullable = false)
    private Float price = null;

    @Column(name = "QUANTITY", length = 5, nullable = false)
    private Float quantity = null;

    @Column(name = "UNIT", nullable = false)
    private Integer unit = null;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy = null;

    @Column(name = "MODIFIED_BY", nullable = false)
    private String modifiedBy = null;

    @DateTimeFormat(pattern = "DD-MM-YYYY[T]HH:mm:ss")
    @Column(name = "CREATED_ON", nullable = false)
    private String createdOn = null;

    @DateTimeFormat(pattern = "DD-MM-YYYY[T]HH:mm:ss")
    @Column(name = "MODIFIED_ON", nullable = false)
    private String modifiedOn = null;

    @Column(name = "STATUS")
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<SalesInventory> salesInventories;

}
