package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.Service;
import info.sasinski.service.ServiceMotService;
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
        value = "/api/service",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController extends ControllerBase {

    final ServiceMotService _serviceMotService;

    @GetMapping
    public ResponseEntity<List<Service>> get() {
        List<Service> list = _serviceMotService.getAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Service> byId(@PathVariable("id") long id) {
        Service service = _serviceMotService.getById(id);
        return service == null ?
                notFound() :
                ok(service);
    }

    @GetMapping("/{id:\\d+}/employee")
    public ResponseEntity<Employee> getEmployeeByServiceId(@PathVariable("id") long id) {
        Service service = _serviceMotService.getById(id);
        if (service != null) {
            Employee employee = service.getEmployee();
            if (employee != null) {
                return ok(employee);
            }
            return notFound();
        }
        return notFound();
    }

    @GetMapping("/{id:\\d+}/customer")
    public ResponseEntity<Customer> getCustomerByServiceId(@PathVariable("id") long id) {
        Service serviceById = _serviceMotService.getById(id);
        if (serviceById == null) {
            return notFound();
        } else {
            Customer customer = serviceById.getCustomer();
            if (customer == null) {
                return notFound();
            }
            return ok(customer);
        }
    }

    @GetMapping("/{id:\\d+}/motorcycleDetails")
    public ResponseEntity<MotorcycleDetails> getMotorcycleDetails(@PathVariable("id") long id) {
        Service byId = _serviceMotService.getById(id);
        if (byId == null) {
            return notFound();
        }
        MotorcycleDetails details = byId.getMotorcycleDetails();
        if (details == null) {
            return notFound();
        }
        return ok(details);
    }

    @GetMapping("/{id:\\d+}/details")
    @ResponseBody
    public ResponseEntity<ActionResponse> get(@PathVariable("id") long id) {
        Service byId = _serviceMotService.getById(id);
        if (byId == null) {
            return notFound();
        }

        MotorcycleDetails motorcycleDetails = byId.getMotorcycleDetails();
        Customer customer = byId.getCustomer();
        Employee employee = byId.getEmployee();

        return ok(new ActionResponse(motorcycleDetails, employee, customer));
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Service service, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _serviceMotService.saveService(service);
        return created();
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> put(@Validated @RequestBody Service service, BindingResult bindingResult, @PathVariable("id") long id) {
        Service byId = _serviceMotService.getById(id);
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

        service.setId(id);
        _serviceMotService.saveService(service);
        return noContent();
    }
}
