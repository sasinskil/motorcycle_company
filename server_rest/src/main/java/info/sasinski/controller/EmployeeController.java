package info.sasinski.controller;

import info.sasinski.entity.Employee;
import info.sasinski.service.EmployeeService;
import info.sasinski.transfer.response.ConstraintViolationsResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeController extends ControllerBase {

    final EmployeeService _employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> get() {
        List<Employee> list = _employeeService.findAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<Employee> get(@PathVariable("id") long id) {
        Employee employeeById = _employeeService.findEmployeeById(id);
        return employeeById == null ?
                notFound() :
                ok(employeeById);
    }

    @GetMapping("/countByFirstName/{firstName}")
    public ResponseEntity<Integer> get(@PathVariable("firstName") String firstName) {
        return ok(_employeeService.countByFirstNameIgnoreCase(firstName));
    }

    @GetMapping("/allByDateOfEmployment/{date}")
    public ResponseEntity<List<Employee>> get(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("date") LocalDate dateOfEmployment) {
        List<Employee> byDateOfEmployment = _employeeService.findAllByDateOfEmployment(dateOfEmployment);
        return byDateOfEmployment.isEmpty() ?
                notFound() :
                ok(byDateOfEmployment);
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _employeeService.saveEmployee(employee);
        return created();
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> put(@Validated @RequestBody Employee employee,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        Employee employeeById = _employeeService.findEmployeeById(id);
        if (employeeById == null) {
            return notFound();
        } else if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        employee.setId(id);
        _employeeService.saveEmployee(employee);
        return noContent();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Employee employeeById = _employeeService.findEmployeeById(id);
        if (employeeById != null) {
            _employeeService.removeEmployee(id);
            return noContent();
        }

        return notFound();
    }
}
