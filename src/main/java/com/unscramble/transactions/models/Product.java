package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Product {

    private String productId;
    private String name;
    private String city;

    public Product() {
    }

    public Product(String productId, String name, String city) {
        this.productId = productId;
        this.name = name;
        this.city = city;
    }
}
