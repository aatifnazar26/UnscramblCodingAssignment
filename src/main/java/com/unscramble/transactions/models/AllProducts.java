package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class AllProducts {

    private List<Product> list;

    public AllProducts() {

    }

    public AllProducts(List<Product> list) {
        this.list = list;
    }
}
