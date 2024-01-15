package com.markethub.smarkethubback.model;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "BILL")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Long idBill;

    @Column(name = "bill_date")
    private Timestamp billDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_account_buyer")
    private Account accountBuyer;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
