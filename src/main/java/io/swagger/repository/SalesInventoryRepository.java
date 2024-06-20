package io.swagger.repository;

import io.swagger.entity.SalesInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesInventoryRepository extends JpaRepository<SalesInventory, Long> {

    @Query("SELECT si FROM SalesInventory si WHERE si.sales.txnId = :txnId")
    List<SalesInventory> findByTxnId(@Param("txnId") String txnId);
}
