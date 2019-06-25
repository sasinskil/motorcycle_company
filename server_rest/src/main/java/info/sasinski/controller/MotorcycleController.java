package info.sasinski.controller;

import info.sasinski.entity.Motorcycle;
import info.sasinski.service.impl.MotorcycleServiceImpl;
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
@RequestMapping(value = "/api/motorcycle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MotorcycleController {

    private final MotorcycleServiceImpl motorcycleService;

    @Autowired
    public MotorcycleController(MotorcycleServiceImpl motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping
    public HttpEntity<List<Motorcycle>> getAll() {

        List<Motorcycle> all = motorcycleService.allMotorcyclesInStock();

        if(!(all.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(all);
        }



        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @GetMapping("/{id:\\d+}")
    public HttpEntity<Motorcycle> getMotorcycleById(@PathVariable("id") long id) {

        Motorcycle motorcycle = motorcycleService.getMotorcycleById(id);

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

    @GetMapping("/byBrand/{brand}")
    HttpEntity<List<Motorcycle>> findByBrand(@PathVariable("brand") String brand) {

        List<Motorcycle> byBrand = motorcycleService.findByBrand(brand);
        List<Motorcycle> allInStock = getAll().getBody();

        if(!(byBrand.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(byBrand);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(allInStock);
        }
    }

    @GetMapping("/powerGreaterThan/{power}")
    HttpEntity<List<Motorcycle>> powerGreaterThan(@PathVariable("power") int pow) {

        List<Motorcycle> byPower = motorcycleService.powerGreaterThan(pow);

        if(!(byPower.isEmpty()))
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(byPower);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);

    }

    @GetMapping("/count")
    public int countMotorcyclesInStock() {

        return motorcycleService.countMotorcyclesInStock();
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public HttpEntity updateMotorcycle(@Validated @RequestBody Motorcycle motorcycle, BindingResult bindingResult, @PathVariable("id") long id) {

        Motorcycle motorcycle1 = motorcycleService.getMotorcycleById(id);

        if(motorcycle1==null)
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

        motorcycle.setId(id);
        motorcycleService.saveMotorcycle(motorcycle);

        return ResponseEntity.noContent().build();

    }



}
