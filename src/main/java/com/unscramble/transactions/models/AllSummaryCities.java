package com.unscramble.transactions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class AllSummaryCities {

    private List<SummaryCity> summary;

    public AllSummaryCities() {

    }

    public AllSummaryCities(List<SummaryCity> list) {
        this.summary = summary;
    }
}
