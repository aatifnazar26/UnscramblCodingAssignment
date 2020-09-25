package com.unscramble.transactions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AllExceptionHandlers {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> productNotFound(ProductNotFoundException exception) {
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectDataFormatException.class)
    public ResponseEntity<Object> incorrectDataFormat(IncorrectDataFormatException exception) {
        return new ResponseEntity<>("The data format is incorrect", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<Object> transactionNotFound(TransactionNotFoundException exception) {
        return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TransactionAlreadyExistsException.class)
    public ResponseEntity<Object> transactionAlreadyExists(TransactionAlreadyExistsException exception) {
        return new ResponseEntity<>("The data format is incorrect", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception exception) {
        return new ResponseEntity<>("Wrong input", HttpStatus.BAD_REQUEST);
    }
}