package io.swagger.repository;

import io.swagger.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, String> {
    @Query("from Sales where txnId = :txnId")
    Optional<Sales> findByTxnId(@Param(value = "txnId") String txnId);
}
