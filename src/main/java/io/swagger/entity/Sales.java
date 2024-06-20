package io.swagger.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SALES")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sales {

    @Id
    @Column(nullable = false, updatable = false, name = "SALES_ID")
    private String salesId = null;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "userId")
    private Users userId = null;

    @Column(name = "TOTAL_AMOUNT")
    private Float totalAmount = null;

    @Column(name = "TXN_ID")
    private String txnId = null;

    @Column(name = "SERVICE_REQUEST_ID")
    private String serviceRequestId = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd[T]HH:mm:ss")
    @Column(name = "TXN_TIMESTAMP")
    private String transactionTimestamp = null;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<SalesInventory> salesInventories;

}
