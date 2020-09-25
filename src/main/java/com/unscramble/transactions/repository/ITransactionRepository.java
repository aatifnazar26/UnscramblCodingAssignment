package com.unscramble.transactions.repository;

import com.unscramble.transactions.exceptions.IncorrectDataFormatException;
import com.unscramble.transactions.exceptions.ProductNotFoundException;
import com.unscramble.transactions.exceptions.TransactionAlreadyExistsException;
import com.unscramble.transactions.exceptions.TransactionNotFoundException;
import com.unscramble.transactions.models.Product;
import com.unscramble.transactions.models.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ITransactionRepository {

    void addTransaction(Transaction transaction) throws TransactionAlreadyExistsException, ProductNotFoundException;
    void addExistingTransactions() throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException, IncorrectDataFormatException;
    void addTransactions(File file) throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException;
    Transaction getTransaction(String id) throws TransactionNotFoundException;
    List<Product> getProducts();
    Map<String,Double> getSummaryByProduct(int n) throws ParseException;
    Map<String,Double> getSummaryByCity(int n) throws ParseException;
}
