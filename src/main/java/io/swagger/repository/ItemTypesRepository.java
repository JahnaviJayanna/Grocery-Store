package io.swagger.repository;

import io.swagger.entity.ItemTypes;
import io.swagger.entity.ItemsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemTypesRepository extends JpaRepository<ItemTypes, String> {

    @Query("from ItemTypes where itemTypeName = :itemTypeName")
    ItemTypes findByItemTypeName(@Param("itemTypeName") String itemTypeName);
}
