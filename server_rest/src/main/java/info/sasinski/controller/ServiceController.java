package info.sasinski.controller;

import info.sasinski.entity.Customer;
import info.sasinski.entity.Employee;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.entity.Service;
import info.sasinski.response.ActionResponse;
import info.sasinski.service.impl.ServiceMotServiceImpl;
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
@RequestMapping(value = "/api/service", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ServiceController {
    private final ServiceMotServiceImpl motService;

    @Autowired
    public ServiceController(ServiceMotServiceImpl motService) {
        this.motService = motService;
    }

    @GetMapping
    public HttpEntity<List<Service>> getAll() {

        List<Service> allServices = motService.getAllS();

        if(!(allServices.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(allServices);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<Service> getServiceById(@PathVariable("id") long id) {

        Service getOneById = motService.getAllS()
                .stream().filter(t -> t.getId() == id).findAny().get();

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
    public HttpEntity<Employee> findEmployeeInService(@PathVariable("id") long id) {
        Employee employee = motService.getAllS()
                .stream().filter(t -> t.getId() == id).findAny().get().getEmployee();

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
    public HttpEntity<Customer> findCustomerInService(@PathVariable("id") long id) {
        Customer customer = motService.getAllS()
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
    public HttpEntity<MotorcycleDetails> findMotorcycleDetailsInService(@PathVariable("id") long id) {

        MotorcycleDetails motorcycleDetails = motService.getAllS()
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
    public HttpEntity<ActionResponse> serviceDetails(@PathVariable("id") long id) {

        Service service = motService.getAllS()
                .stream().filter(t -> t.getId() == id).findAny().get();

        MotorcycleDetails motorcycleDetails = service.getMotorcycleDetails();
        Customer customer = service.getCustomer();
        Employee employee = service.getEmployee();

        ActionResponse actionResponse = new ActionResponse(motorcycleDetails, employee, customer);

        if (service != null)
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
    public HttpEntity saveService(@Validated @RequestBody Service service, BindingResult bindingResult) {

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

        motService.saveService(service);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistService(@Validated @RequestBody Service service,BindingResult bindingResult, @PathVariable("id") long id) {

        Service service1 = motService.getAllS()
                .stream().filter(t -> t.getId() == id).findAny().get();

        if(service1==null)
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

        service.setId(id);
        motService.saveService(service);

        return ResponseEntity.noContent().build();

    }

}
