package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class SummaryProduct {

    private String productName;
    private double totalAmount;

    public SummaryProduct() {

    }

    public SummaryProduct(String productName, double totalAmount) {
        this.productName = productName;
        this.totalAmount = totalAmount;
    }
}
