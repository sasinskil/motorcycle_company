package info.sasinski.controller;

import info.sasinski.entity.Address;
import info.sasinski.service.AddressService;
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

@RestController
@RequestMapping(value = "/api/address")
@CrossOrigin(origins = "*")
public class AddressController extends ControllerBase {

    final AddressService _addressService;

    @GetMapping
    public ResponseEntity<List<Address>> get() {
        List<Address> list = _addressService.findAll();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Address> get(@PathVariable("id") long id) {
        Address addressById = _addressService.findAddressById(id);
        return addressById == null ?
                notFound() :
                ok(addressById);
    }

    @PutMapping("/save/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> put(@Validated @RequestBody Address address,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        Address addressById = _addressService.findAddressById(id);
        if (addressById == null) {
            return notFound();
        } else if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        address.setId(id);
        _addressService.saveAddress(address);

        return noContent();
    }
}