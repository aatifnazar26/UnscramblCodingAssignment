package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class AllSummaryProducts {

    private List<SummaryProduct> summary;

    public AllSummaryProducts() {

    }

    public AllSummaryProducts(List<SummaryProduct> list) {
        this.summary = summary;
    }
}
