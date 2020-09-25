package com.unscramble.transactions.services;

import com.unscramble.transactions.exceptions.ProductNotFoundException;
import com.unscramble.transactions.exceptions.TransactionAlreadyExistsException;
import com.unscramble.transactions.exceptions.TransactionNotFoundException;
import com.unscramble.transactions.models.Product;
import com.unscramble.transactions.models.Transaction;
import com.unscramble.transactions.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    ITransactionRepository repository;

    @Autowired
    public TransactionService(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction getTransaction(String transaction_id) throws TransactionNotFoundException {
        return repository.getTransaction(transaction_id);
    }

    public void addTransactions(File file) throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException {
        repository.addTransactions(file);
    }

    public Map<String,Double> getSummaryByProduct(int n) throws ParseException {
        return repository.getSummaryByProduct(n);
    }

    public Map<String,Double> getSummaryByCity(int n) throws ParseException {
        return repository.getSummaryByCity(n);
    }

    public List<Product> getProducts() {
        return repository.getProducts();
    }
}