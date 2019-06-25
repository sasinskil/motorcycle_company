package info.sasinski.repository;


import info.sasinski.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Customer C SET C.lastName = :newLastName WHERE C.id = :id")
    void setLastNameforCustomer(@Param("newLastName") String newLastName, @Param("id") long id);

    Customer findCustomerById(long id);

}
