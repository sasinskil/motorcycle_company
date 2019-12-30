package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.TestDrive;
import info.sasinski.service.TestDriveService;
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
        value = "/api/testDrive",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestDriveController extends ControllerBase {

    final TestDriveService _testDriveService;

    @GetMapping
    public ResponseEntity<List<TestDrive>> get() {
        List<TestDrive> list = _testDriveService.getAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<TestDrive> getTestDriveById(@PathVariable("id") long id) {
        TestDrive byId = _testDriveService.getById(id);
        return byId == null ?
                notFound() :
                ok(byId);
    }

    @GetMapping("/{id:\\d+}/employee")
    public ResponseEntity<Employee> findEmployeeInTestDrive(@PathVariable("id") long id) {
        TestDrive byId = _testDriveService.getById(id);
        if (byId == null) {
            return notFound();
        }

        Employee employee = byId.getEmployee();
        if (employee == null) {
            return notFound();
        }

        return ok(employee);
    }

    @GetMapping("/{id:\\d+}/customer")
    public ResponseEntity<Customer> findCustomerInTestDrive(@PathVariable("id") long id) {
        TestDrive byId = _testDriveService.getById(id);
        if (byId == null) {
            return notFound();
        }

        Customer customer = byId.getCustomer();
        if (customer == null) {
            return notFound();
        }

        return ok(customer);
    }

    @GetMapping("/{id:\\d+}/motorcycleDetails")
    public ResponseEntity<MotorcycleDetails> findMotorcycleDetailsInTestDrive(@PathVariable("id") long id) {
        TestDrive byId = _testDriveService.getById(id);
        if (byId == null) {
            return notFound();
        }

        MotorcycleDetails motorcycleDetails = byId.getMotorcycleDetails();
        if (motorcycleDetails == null) {
            return notFound();
        }

        return ok(motorcycleDetails);
    }

    @GetMapping("/{id:\\d+}/details")
    @ResponseBody
    public ResponseEntity<ActionResponse> testDriveDetails(@PathVariable("id") long id) {
        TestDrive byId = _testDriveService.getById(id);
        if (byId == null) {
            return notFound();
        }

        MotorcycleDetails motorcycleDetails = byId.getMotorcycleDetails();
        Customer customer = byId.getCustomer();
        Employee employee = byId.getEmployee();

        return ok(new ActionResponse(motorcycleDetails, employee, customer));
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody TestDrive testDrive, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }
        _testDriveService.saveTestDrive(testDrive);
        return created();

    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> put(@Validated @RequestBody TestDrive testDrive,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        TestDrive byId = _testDriveService.getById(id);
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

        testDrive.setId(id);
        _testDriveService.saveTestDrive(testDrive);

        return noContent();
    }
}
