package info.sasinski.service;

import info.sasinski.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllT();

    void saveTransaction(Transaction transaction);
}
