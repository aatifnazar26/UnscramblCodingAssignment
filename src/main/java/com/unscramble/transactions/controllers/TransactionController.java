package com.unscramble.transactions.controllers;

import com.unscramble.transactions.exceptions.ProductNotFoundException;
import com.unscramble.transactions.exceptions.TransactionAlreadyExistsException;
import com.unscramble.transactions.exceptions.TransactionNotFoundException;
import com.unscramble.transactions.models.*;
import com.unscramble.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RestController
@RequestMapping("/assignment")
public class TransactionController {

    private TransactionService service;

    public TransactionController() {

    }

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping(value="/transaction/{transaction_id}")
    public ResponseEntity<?> getTransaction(@PathVariable("transaction_id") String transaction_id) throws TransactionNotFoundException {
        Transaction transaction = service.getTransaction(transaction_id);
        return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
    }

    @GetMapping(value="/transactionSummaryByProducts/{last_n_days}")
    public ResponseEntity<?>  getTransactionSummaryByProducts(@PathVariable("last_n_days") int last_n_days) throws ParseException {
        Map<String,Double> map = service.getSummaryByProduct(last_n_days);
        List<SummaryProduct> summary = new ArrayList<>();
        for(String key : map.keySet()) {
            SummaryProduct summaryProduct = new SummaryProduct(key,map.get(key));
            summary.add(summaryProduct);
        }
        AllSummaryProducts allSummaryProducts = new AllSummaryProducts();
        allSummaryProducts.setSummary(summary);
        return new ResponseEntity<AllSummaryProducts>(allSummaryProducts, HttpStatus.OK);
    }

    @GetMapping(value="/transactionSummaryByManufacturingCity/{last_n_days}")
    public ResponseEntity<?>  getTransactionSummaryByCity(@PathVariable("last_n_days") int last_n_days) throws ParseException {
        Map<String,Double> map = service.getSummaryByCity(last_n_days);
        List<SummaryCity> summary = new ArrayList<>();
        for(String key : map.keySet()) {
            SummaryCity summaryCity = new SummaryCity(key,map.get(key));
            summary.add(summaryCity);
        }
        AllSummaryCities allSummaryCities = new AllSummaryCities();
        allSummaryCities.setSummary(summary);
        return new ResponseEntity<AllSummaryCities>(allSummaryCities, HttpStatus.OK);
    }

    @GetMapping(value="/products")
    public ResponseEntity<?> getProducts() {
        List<Product> list = service.getProducts();
        AllProducts allProducts = new AllProducts(list);
        return new ResponseEntity<AllProducts>(allProducts, HttpStatus.OK);
    }

    public void addTransactions(File file) throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException {
        service.addTransactions(file);
    }
}