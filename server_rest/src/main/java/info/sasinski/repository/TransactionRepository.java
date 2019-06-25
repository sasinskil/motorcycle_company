package info.sasinski.repository;


import info.sasinski.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {


    @Query("SELECT T FROM Transaction T")
    List<Transaction> getAllT();
}
