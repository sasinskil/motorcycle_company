package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.service.impl.CustomerServiceImpl;
import info.sasinski.validationResponse.ConstraintViolationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public HttpEntity<List<Customer>> getCustomers() {

        List<Customer> getAll = customerService.findAll();

        if(getAll != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getAll);
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<Customer> findCustomerById(@PathVariable("id") long id) {

        Customer customer = customerService.findCustomerById(id);

        if(customer != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(customer);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @PutMapping("/change/{id:\\d+}/{newLastName}")
    public HttpEntity<Void> setLastNameforCustomer
            (@PathVariable("id") long id, @PathVariable("newLastName") String newLastName)
    {
        Customer customer = customerService.findCustomerById(id);

        if(customer != null) {
            customerService.setLastNameforCustomer(newLastName, id);

            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

    }

    @PostMapping
    public HttpEntity saveCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {


        if(bindingResult.hasErrors())
        {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());

            ConstraintViolationsResponse responseValidateErrors = new ConstraintViolationsResponse("409","Validation failure",errors);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(responseValidateErrors);

        }

        customerService.saveCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistCustomer(@Validated @RequestBody Customer customer,BindingResult bindingResult, @PathVariable("id") long id) {

        Customer customer1 = customerService.findCustomerById(id);

        if(customer1==null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        else if (bindingResult.hasErrors())
        {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());

            ConstraintViolationsResponse responseValidateErrors = new ConstraintViolationsResponse("409","Validation failure",errors);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(responseValidateErrors);

        }

        customer.setId(id);
        customerService.saveCustomer(customer);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id:\\d+}")
    public HttpEntity<Void> removeCustomer(@PathVariable("id") long id) {

        Customer customer = customerService.findCustomerById(id);

        if (customer != null) {
            customerService.removeCustomer(id);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

    }
}
