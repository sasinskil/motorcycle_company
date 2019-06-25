package info.sasinski.controller;

import info.sasinski.entity.*;
import info.sasinski.response.ActionResponse;
import info.sasinski.service.impl.TestDriveServiceImpl;
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
@RequestMapping(value = "/api/testDrive", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestDriveController {

    private final TestDriveServiceImpl testDriveService;

    @Autowired
    public TestDriveController(TestDriveServiceImpl testDriveService) {
        this.testDriveService = testDriveService;
    }

    @GetMapping
    public HttpEntity<List<TestDrive>> getAll() {

        List<TestDrive> allTD = testDriveService.getAllTD();

        if(!(allTD.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(allTD);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<TestDrive> getTestDriveById(@PathVariable("id") long id) {

        TestDrive getOneById = testDriveService.getAllTD()
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .get();

        if(getOneById != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getOneById);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}/employee")
    public HttpEntity<Employee> findEmployeeInTestDrive(@PathVariable("id") long id) {
        Employee employee = testDriveService.getAllTD()
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .get().getEmployee();

        if(employee != null)
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
    public HttpEntity<Customer> findCustomerInTestDrive(@PathVariable("id") long id) {
        Customer customer = testDriveService.getAllTD()
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .get().getCustomer();

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
    public HttpEntity<MotorcycleDetails> findMotorcycleDetailsInTestDrive(@PathVariable("id") long id) {
        MotorcycleDetails motorcycleDetails = testDriveService.getAllTD()
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .get().getMotorcycleDetails();

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
    public HttpEntity<ActionResponse> testDriveDetails(@PathVariable("id") long id) {

        TestDrive testDrive = testDriveService.getAllTD()
                .stream()
                .filter(t -> t.getId() == id)
                .findAny()
                .get();

        MotorcycleDetails motorcycleDetails = testDrive.getMotorcycleDetails();
        Customer customer = testDrive.getCustomer();
        Employee employee = testDrive.getEmployee();

        ActionResponse actionResponse = new ActionResponse(motorcycleDetails, employee, customer);

        if(testDrive != null)
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
    public HttpEntity saveTestDrive(@Validated @RequestBody TestDrive testDrive, BindingResult bindingResult) {


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

        testDriveService.saveTestDrive(testDrive);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistTestDrive(@Validated @RequestBody TestDrive testDrive,BindingResult bindingResult, @PathVariable("id") long id) {

        TestDrive testDrive1 = testDriveService.getAllTD()
                .stream().filter(t -> t.getId() == id).findAny().get();

        if(testDrive1==null)
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

        testDrive.setId(id);
        testDriveService.saveTestDrive(testDrive);

        return ResponseEntity.noContent().build();

    }
}
