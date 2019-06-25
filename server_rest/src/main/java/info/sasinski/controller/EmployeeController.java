package info.sasinski.controller;

import info.sasinski.entity.Employee;
import info.sasinski.service.impl.EmployeeServiceImpl;
import info.sasinski.validationResponse.ConstraintViolationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public HttpEntity<List<Employee>> getEmployees() {

        List<Employee> getAll = employeeService.findAll();

        if(!(getAll.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getAll);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping(value = "/{id:\\d+}")
    public HttpEntity<Employee> findEmployeeById(@PathVariable("id") long id) {
        Employee emp = employeeService.findEmployeeById(id);

        if(emp != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(emp);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/countByFirstName/{firstName}")
    public int countByFirstNameIgnoreCase(@PathVariable("firstName") String firstName) {

        return employeeService.countByFirstNameIgnoreCase(firstName);
    }

    @GetMapping("/allByDateOfEmployment/{date}")
    public HttpEntity<List<Employee>> findAllByDateOfEmployment
            (@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate dateOfEmployment) {

        List<Employee> byDateOfEmployment = employeeService.findAllByDateOfEmployment(dateOfEmployment);

        if(!(byDateOfEmployment.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(byDateOfEmployment);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @PostMapping
    public HttpEntity saveEmployee(@Validated @RequestBody Employee employee, BindingResult bindingResult) {

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

        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistEmployee(@Validated @RequestBody Employee employee,BindingResult bindingResult, @PathVariable("id") long id) {

        Employee employee1 = employeeService.findEmployeeById(id);

        if(employee1==null)
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

        employee.setId(id);
        employeeService.saveEmployee(employee);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id:\\d+}")
    public HttpEntity<Void> removeEmployee(@PathVariable("id") long id) {

        Employee employee = employeeService.findEmployeeById(id);

        if (employee != null) {
            employeeService.removeEmployee(id);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

    }
}
