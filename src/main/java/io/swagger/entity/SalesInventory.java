package io.swagger.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SALES_INVENTORY")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_inventory_seq")
    @SequenceGenerator(name = "sales_inventory_seq", sequenceName = "sales_inventory_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TXN_ID", nullable = false)
    private Sales sales;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Inventory inventory;

    @Column(name = "UNITS_SOLD", nullable = false)
    private int unitsSold;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
}

