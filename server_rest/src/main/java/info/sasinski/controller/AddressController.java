package info.sasinski.controller;

import info.sasinski.entity.Address;
import info.sasinski.service.impl.AddressServiceImpl;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/address")
@CrossOrigin(origins = "*")
public class AddressController {

    private final AddressServiceImpl addressService;

    @Autowired
    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public HttpEntity<List<Address>> getAll() {

            List<Address> getAllA = addressService.findAll();

            if(getAllA !=null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(getAllA);
            }

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);

    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<Address> getAddressById(@PathVariable("id") long id) {

            Address address = addressService.findAddressById(id);

            if(address !=null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(address);
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
    }

    @PutMapping("/save/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistAddress(@Validated @RequestBody Address address, BindingResult bindingResult, @PathVariable("id") long id) {

        Address address1 = addressService.findAddressById(id);

        if(address1==null)
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

            address.setId(id);

            addressService.saveAddress(address);

            return ResponseEntity.noContent().build();

    }
}