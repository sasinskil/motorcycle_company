package info.sasinski.controller;


import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.Transaction;
import info.sasinski.response.ActionResponse;
import info.sasinski.service.impl.TransactionServiceImpl;
import info.sasinski.validationResponse.ConstraintViolationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public HttpEntity<Iterable<Transaction>> getAll() {

        Iterable<Transaction> findALL = transactionService.getAllT();

        if(!(((List<Transaction>) findALL).isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(findALL);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<Transaction> getTransactionById(@PathVariable("id") long id) {
        Transaction getOneById = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get();

        if(getOneById != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getOneById);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/{id:\\d+}/employee")
    public HttpEntity<Employee> findEmployeeInTransaction(@PathVariable("id") long id) {
        Employee employee = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get().getEmployee();

        if (employee != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(employee);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}/customer")
    public HttpEntity<Customer> findCustomerInTransaction(@PathVariable("id") long id) {
        Customer customer = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get().getCustomer();

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

    @GetMapping("/{id:\\d+}/motorcycleDetails")
    public HttpEntity<MotorcycleDetails> findMotorcycleDetailsInTransaction(@PathVariable("id") long id) {
        MotorcycleDetails motorcycleDetails = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get().getMotorcycleDetails();

        if(motorcycleDetails != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(motorcycleDetails);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}/details")
    @ResponseBody
    public HttpEntity<ActionResponse> transactionDetails(@PathVariable("id") long id) {

        Transaction transaction = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get();

        MotorcycleDetails motorcycleDetails = transaction.getMotorcycleDetails();
        Customer customer = transaction.getCustomer();
        Employee employee = transaction.getEmployee();

        ActionResponse actionResponse = new ActionResponse(motorcycleDetails, employee, customer);

        if(transaction != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(actionResponse);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @PostMapping
    public HttpEntity saveTransaction(@Validated @RequestBody Transaction transaction, BindingResult bindingResult) {


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

        transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistTransaction(@Validated @RequestBody Transaction transaction,BindingResult bindingResult, @PathVariable("id") long id) {

        Transaction transaction1 = transactionService.getAllT()
                .stream().filter(t -> t.getId() == id).findAny().get();

        if(transaction1==null)
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

        transaction.setId(id);
        transactionService.saveTransaction(transaction);

        return ResponseEntity.noContent().build();

    }


}
