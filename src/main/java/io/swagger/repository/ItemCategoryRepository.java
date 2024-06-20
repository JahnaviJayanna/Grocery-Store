package io.swagger.repository;

import io.swagger.entity.ItemsCategory;
import io.swagger.model.InventoryItemDetailsPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemCategoryRepository extends JpaRepository<ItemsCategory, String> {

    @Query("from ItemsCategory where categoryName = :categoryName")
    ItemsCategory findByCategoryName(@Param("categoryName") String categoryName);
}
