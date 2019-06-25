package info.sasinski.service.impl;

import info.sasinski.entity.Customer;
import info.sasinski.repository.CustomerRepository;
import info.sasinski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void setLastNameforCustomer(String newLastName, long id) {
        customerRepository.setLastNameforCustomer(newLastName,id);

    }

    @Override
    public Customer findCustomerById(long id) {

        Customer customer = customerRepository.findCustomerById(id);

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void removeCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
