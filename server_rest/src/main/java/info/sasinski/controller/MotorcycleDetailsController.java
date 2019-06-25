package info.sasinski.controller;

import info.sasinski.entity.Motorcycle;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.service.impl.MotorcycleDetailsServiceImpl;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/motorcycleDetails")
public class MotorcycleDetailsController {
    private final MotorcycleDetailsServiceImpl motorcycleDetailsService;

    @Autowired
    public MotorcycleDetailsController(MotorcycleDetailsServiceImpl motorcycleDetailsService) {
        this.motorcycleDetailsService = motorcycleDetailsService;
    }

    @GetMapping
    public HttpEntity<List<MotorcycleDetails>> getAll() {

        List<MotorcycleDetails> mDList = motorcycleDetailsService.allMotorcyclesInStock();

        if(!(mDList.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mDList);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping(value = "/{id:\\d+}")
    public HttpEntity<MotorcycleDetails> getMotorcycleDetailsById(@PathVariable("id") long id) {

        MotorcycleDetails motorcycleDetails = motorcycleDetailsService.findMotorcycleDetailsById(id);

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

    @GetMapping("/{id:\\d+}/motorcycle")
    public HttpEntity<Motorcycle> getMotorcycleFromMotorcycleDetails(@PathVariable("id") long id) {

        Motorcycle motorcycle = motorcycleDetailsService.findMotorcycleDetailsById(id).getMotorcycle();


        if(motorcycle != null)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(motorcycle);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/count")
    public int countMotorcyclesInStock() {
        return motorcycleDetailsService.countMotorcyclesInStock();
    }

    @GetMapping("/highestPrice")
    public HttpEntity<List<MotorcycleDetails>> findMotorcycleWithHighestPrice() {

        List<MotorcycleDetails> byPrice = motorcycleDetailsService.findMotorcycleWithHighestPrice();

        if(!(byPrice.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(byPrice);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    @PostMapping
    public HttpEntity saveSimpleMotorcycle(@Validated @RequestBody MotorcycleDetails motorcycleDetails, BindingResult bindingResult) {

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

        motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateExistSimpleMotorcycle(@Validated @RequestBody MotorcycleDetails motorcycleDetails,BindingResult bindingResult, @PathVariable("id") long id) {

        MotorcycleDetails motorcycleDetails1 = motorcycleDetailsService.findMotorcycleDetailsById(id);

        if(motorcycleDetails1==null)
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

        motorcycleDetails.setId(id);
        motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id:\\d+}")
    public HttpEntity<Void> removeSimpleMotorcycle(@PathVariable("id") long id) {

        MotorcycleDetails motorcycleDetails = motorcycleDetailsService.findMotorcycleDetailsById(id);

        if (motorcycleDetails != null) {
            motorcycleDetailsService.removeSimpleMotorcycle(id);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();

    }


}
