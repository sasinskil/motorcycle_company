package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.Transaction;
import info.sasinski.service.TransactionService;
import info.sasinski.transfer.response.ActionResponse;
import info.sasinski.transfer.response.ConstraintViolationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@RestController
@RequestMapping(
        value = "/api/transaction",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController extends ControllerBase {

    final TransactionService _transactionService;

    @GetMapping
    public ResponseEntity<Iterable<Transaction>> get() {
        List<Transaction> list = _transactionService.getAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Transaction> get(@PathVariable("id") long id) {
        Transaction transaction = _transactionService.getById(id);
        return transaction == null ?
                notFound() :
                ok(transaction);
    }

    @GetMapping("/{id:\\d+}/employee")
    public ResponseEntity<Employee> findEmployeeInTransaction(@PathVariable("id") long id) {
        Transaction transaction = _transactionService.getById(id);
        if (transaction == null) {
            return notFound();
        }

        Employee employee = transaction.getEmployee();
        if (employee == null) {
            return notFound();
        }

        return ok(employee);
    }

    @GetMapping("/{id:\\d+}/customer")
    public ResponseEntity<Customer> findCustomerInTransaction(@PathVariable("id") long id) {
        Transaction transaction = _transactionService.getById(id);
        if (transaction == null) {
            return notFound();
        }

        Customer customer = transaction.getCustomer();
        if (customer == null) {
            return notFound();
        }

        return ok(customer);
    }

    @GetMapping("/{id:\\d+}/motorcycleDetails")
    public ResponseEntity<MotorcycleDetails> findMotorcycleDetailsInTransaction(@PathVariable("id") long id) {
        Transaction transaction = _transactionService.getById(id);
        if (transaction == null) {
            return notFound();
        }

        MotorcycleDetails motorcycleDetails = transaction.getMotorcycleDetails();
        if (motorcycleDetails == null) {
            return notFound();
        }

        return ok(motorcycleDetails);
    }

    @GetMapping("/{id:\\d+}/details")
    @ResponseBody
    public ResponseEntity<ActionResponse> transactionDetails(@PathVariable("id") long id) {
        Transaction transaction = _transactionService.getById(id);
        if (transaction == null) {
            return notFound();
        }

        MotorcycleDetails motorcycleDetails = transaction.getMotorcycleDetails();
        Customer customer = transaction.getCustomer();
        Employee employee = transaction.getEmployee();

        return ok(new ActionResponse(motorcycleDetails, employee, customer));
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Transaction transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _transactionService.saveTransaction(transaction);
        return created();

    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> put(@Validated @RequestBody Transaction transaction,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {
        Transaction byId = _transactionService.getById(id);
        if (byId == null) {
            return notFound();
        }
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        transaction.setId(id);
        _transactionService.saveTransaction(transaction);

        return noContent();
    }
}
