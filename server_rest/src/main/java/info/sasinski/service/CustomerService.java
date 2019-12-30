package info.sasinski.service;

import info.sasinski.entity.Customer;
import info.sasinski.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class CustomerService {

    final CustomerRepository _customerRepository;

    public void setLastNameForCustomer(String newLastName, long id) {
        _customerRepository.setLastNameForCustomer(newLastName, id);
    }

    public Customer findCustomerById(long id) {
        return _customerRepository.findById(id).orElse(null);
    }

    public List<Customer> findAll() {
        return _customerRepository.findAll();
    }

    public void removeCustomer(long id) {
        _customerRepository.deleteById(id);
    }

    public void saveCustomer(Customer customer) {
        _customerRepository.save(customer);
    }
}
