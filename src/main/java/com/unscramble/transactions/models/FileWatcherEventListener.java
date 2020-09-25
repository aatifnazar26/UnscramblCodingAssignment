package com.unscramble.transactions.models;

import com.unscramble.transactions.controllers.TransactionController;
import com.unscramble.transactions.exceptions.ProductNotFoundException;
import com.unscramble.transactions.exceptions.TransactionAlreadyExistsException;
import com.unscramble.transactions.repository.TransactionRepository;
import com.unscramble.transactions.services.TransactionService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Component
@Getter
@Setter
public class FileWatcherEventListener implements FileChangeListener{

    private static TransactionService transactionService;

    public FileWatcherEventListener() {
    }

    @Autowired
    public FileWatcherEventListener(TransactionService transactionService) {
        FileWatcherEventListener.transactionService = transactionService;
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles changedFiles : changeSet) {
            for(ChangedFile changedFile : changedFiles.getFiles()) {
                if(changedFile.getType().equals(ChangedFile.Type.ADD) && !isLocked(changedFile.getFile().toPath())) {
                    System.out.println("Operation: " + changedFile.getType() + " On file: "+ changedFile.getFile().getName() + " is done");
                    try {
                        transactionService.addTransactions(changedFile.getFile());
                    } catch (TransactionAlreadyExistsException e) {
                        e.printStackTrace();
                    } catch (ProductNotFoundException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE); FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }
}