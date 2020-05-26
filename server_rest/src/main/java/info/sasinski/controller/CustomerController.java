package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.service.CustomerService;
import info.sasinski.transfer.response.ConstraintViolationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController extends ControllerBase {

    final CustomerService _customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> get() {
        List<Customer> list = _customerService.findAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Customer> get(@PathVariable("id") long id) {
        Customer customerById = _customerService.findCustomerById(id);
        return customerById == null ?
                notFound() :
                ok(customerById);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ok(_customerService.countCustomers());
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _customerService.saveCustomer(customer);
        return created();
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> put(@Validated @RequestBody Customer customer,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {
        Customer customerById = _customerService.findCustomerById(id);
        if (customerById == null) {
            return notFound();
        } else if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        customer.setId(id);
        _customerService.saveCustomer(customer);

        return noContent();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Customer customerById = _customerService.findCustomerById(id);
        if (customerById != null) {
            _customerService.removeCustomer(id);
            return noContent();
        }
        return notFound();
    }
}
