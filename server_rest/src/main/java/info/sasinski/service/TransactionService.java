package info.sasinski.service;

import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.Transaction;
import info.sasinski.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class TransactionService {

    final TransactionRepository _transactionRepository;
    final MotorcycleDetailsService _motorcycleDetailsService;

    public List<Transaction> getAll() {
        return _transactionRepository.findAll();
    }

    public long countTransactions() { return _transactionRepository.count(); }

    public void saveTransaction(Transaction transaction) {

        boolean checkIsSold = transaction.getMotorcycleDetails().getIsSold();
        MotorcycleDetails motorcycleDetails = transaction.getMotorcycleDetails();

        if(!checkIsSold) {
            motorcycleDetails.setIsSold(Boolean.TRUE);
            _motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);
        }
        _transactionRepository.save(transaction);
    }

    public Transaction getById(long id) {
        return _transactionRepository
                .findById(id)
                .orElse(null);
    }

    public void removeTransaction(long id) {
        Transaction transaction = getById(id);
        MotorcycleDetails motorcycleDetails = transaction.getMotorcycleDetails();

        motorcycleDetails.setIsSold(Boolean.FALSE);
        _motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);

        _transactionRepository.deleteById(id);
    }
}
