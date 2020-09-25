package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class SummaryCity {

    private String cityName;
    private double totalAmount;

    public SummaryCity() {

    }

    public SummaryCity(String productName, double totalAmount) {
        this.cityName = productName;
        this.totalAmount = totalAmount;
    }
}
