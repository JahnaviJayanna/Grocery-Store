package io.swagger.repository;

import io.swagger.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

    @Query("from Inventory where itemName = :itemName")
    Inventory findByItemName(@Param("itemName") String itemName);
}
