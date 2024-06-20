package io.swagger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String salesId = null;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "userId")
    private Users userId = null;

    @Column(name = "TOTAL_AMOUNT",nullable = false)
    @NotEmpty
    private Float totalAmount = null;

    @Column(name = "TXN_ID", nullable = false)
    @NotEmpty
    private String txnId = null;

    @Column(name = "SERVICE_REQUEST_ID", nullable = false)
    @NotEmpty
    private String serviceRequestId = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd[T]HH:mm:ss")
    @Column(name = "TXN_TIMESTAMP", nullable = false)
    @NotEmpty
    private String transactionTimestamp = null;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    @NotEmpty
    private List<SalesInventory> salesInventories;

}
