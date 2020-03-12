package info.sasinski.service;

import info.sasinski.entity.Transaction;
import info.sasinski.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class TransactionService {

    final TransactionRepository _transactionRepository;

    public List<Transaction> getAll() {
        return _transactionRepository.findAll();
    }

    public long countTransactions() { return _transactionRepository.count(); }

    public void saveTransaction(Transaction transaction) {
        _transactionRepository.save(transaction);
    }

    public Transaction getById(long id) {
        return _transactionRepository
                .findById(id)
                .orElse(null);
    }

    public void removeTransaction(long id) {
        _transactionRepository.deleteById(id);
    }
}
