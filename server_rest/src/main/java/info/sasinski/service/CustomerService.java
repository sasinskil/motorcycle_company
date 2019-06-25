package info.sasinski.service;

import info.sasinski.entity.Customer;

import java.util.List;

public interface CustomerService {

    void setLastNameforCustomer( String newLastName,long id);

    Customer findCustomerById(long id);

    List<Customer> findAll();

    void removeCustomer(long id);

    void saveCustomer(Customer customer);
}
