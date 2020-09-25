package com.unscramble.transactions.repository;

import com.unscramble.transactions.exceptions.IncorrectDataFormatException;
import com.unscramble.transactions.exceptions.ProductNotFoundException;
import com.unscramble.transactions.exceptions.TransactionAlreadyExistsException;
import com.unscramble.transactions.exceptions.TransactionNotFoundException;
import com.unscramble.transactions.models.Product;
import com.unscramble.transactions.models.Transaction;
import com.unscramble.transactions.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

@Repository
public class TransactionRepository implements ITransactionRepository {

    private Environment environment;
    private Map<String,Transaction> transactions;
    private Map<String, Product> products;
    private Map<String,Double> summaryByProduct;
    private Map<String,Double> summaryByCity;
    private String staticFilePath;
    private String transactionFilePath;
    private String delimiter;
    private Util util;
    private static TransactionRepository instance;

    public static TransactionRepository getInstance() throws ProductNotFoundException, FileNotFoundException, TransactionAlreadyExistsException, IncorrectDataFormatException {
        if(instance == null)
            instance = new TransactionRepository();
        return instance;
    }

    public TransactionRepository () {

    }

    @Autowired
    public TransactionRepository(Environment environment, Util util) throws FileNotFoundException, TransactionAlreadyExistsException, ProductNotFoundException, IncorrectDataFormatException {
        this.environment = environment;
        this.util = util;
        transactions = new TreeMap<>(Collections.reverseOrder());
        products = new TreeMap<>();
        staticFilePath = environment.getProperty("staticFilePath");
        transactionFilePath = environment.getProperty("transactionFilePath");
        delimiter = environment.getProperty("delimiter");
        addProducts();
        addExistingTransactions();
    }

    private void addProducts() throws FileNotFoundException {
        File file = new File(staticFilePath);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String arr[] = line.split(delimiter);
            Product product = new Product(arr[0].trim(),arr[1].trim(),arr[2].trim());
            products.put(product.getProductId(),product);
        }
    }

    public void addExistingTransactions() throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException, IncorrectDataFormatException {

        File folder = new File(transactionFilePath);
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles.length > 0) {
            for (File file : listOfFiles) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String arr[] = line.split(delimiter);
                    if(arr.length != 4)
                        throw new IncorrectDataFormatException("The data format is incorrect");
                    Transaction transaction = new Transaction(arr[0].trim(), arr[1].trim(), Double.parseDouble(arr[2].trim()), arr[3].trim());
                    addTransaction(transaction);
                }
                scanner.close();
            }
        }
    }

    public void addTransactions(File file) throws TransactionAlreadyExistsException, ProductNotFoundException, FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String arr[] = line.split(delimiter);
            Transaction transaction = new Transaction(arr[0].trim(), arr[1].trim(), Double.parseDouble(arr[2].trim()), arr[3].trim());
            addTransaction(transaction);
        }
        scanner.close();
    }

    @Override
    public void addTransaction(Transaction transaction) throws TransactionAlreadyExistsException, ProductNotFoundException {
        if (transactions.containsKey(transaction.getTransactionId()))
            throw new TransactionAlreadyExistsException("This transaction has been processed before");
        if (!products.containsKey(transaction.getProductId()))
            throw new ProductNotFoundException("Product not found");
        transactions.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public Transaction getTransaction(String id) throws TransactionNotFoundException {
        if(!transactions.containsKey(id))
            throw new TransactionNotFoundException("Transaction not found");
        return transactions.get(id);
    }

    @Override
    public Map<String,Double> getSummaryByProduct(int n) throws ParseException {
        summaryByProduct = new TreeMap<>();
        for(String id : transactions.keySet())
        {
            Transaction transaction = transactions.get(id);
            int days = util.compareDate(transaction.getTimestamp());
            if(days <= n) {
                String productName = products.get(transaction.getProductId()).getName();
                double amount = transaction.getAmount();
                if(summaryByProduct.containsKey(productName)) {
                    double oldAmount = summaryByProduct.get(productName);
                    summaryByProduct.put(productName,amount+oldAmount);
                }
                else
                    summaryByProduct.put(productName,amount);
            }
            else
                break;
        }
        return summaryByProduct;
    }

    @Override
    public Map<String,Double> getSummaryByCity(int n) throws ParseException {
        summaryByCity = new TreeMap<>();
        for(String id : transactions.keySet())
        {
            Transaction transaction = transactions.get(id);
            int days = util.compareDate(transaction.getTimestamp());
            if(days <= n) {
                String cityName = products.get(transaction.getProductId()).getCity();
                double amount = transaction.getAmount();
                if(summaryByCity.containsKey(cityName)) {
                    double oldAmount = summaryByCity.get(cityName);
                    summaryByCity.put(cityName,amount+oldAmount);
                }
                else
                    summaryByCity.put(cityName,amount);
            }
            else
                break;
        }
        return summaryByCity;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        for(String id : products.keySet()) {
            list.add(products.get(id));
        }
        return list;
    }
}