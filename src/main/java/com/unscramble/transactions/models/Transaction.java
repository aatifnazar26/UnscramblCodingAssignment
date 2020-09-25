package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Transaction {

    private String transactionId;
    private String productId;
    private double amount;
    private String timestamp;

    public Transaction() {

    }
    public Transaction(String transactionId, String productId, double amount, String timestamp) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
