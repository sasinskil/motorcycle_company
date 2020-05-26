package info.sasinski.controller;

import info.sasinski.entity.Motorcycle;
import info.sasinski.service.MotorcycleService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(
        value = "/api/motorcycle")
public class MotorcycleController extends ControllerBase {

    final MotorcycleService _motorcycleService;

    @GetMapping
    public ResponseEntity<List<Motorcycle>> get() {
        List<Motorcycle> list = _motorcycleService.allMotorcyclesInStock();
        return list.isEmpty() ?
                notFound() :
                ok(list);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Motorcycle> get(@PathVariable("id") long id) {
        Motorcycle motorcycleById = _motorcycleService.getMotorcycleById(id);
        return motorcycleById == null ?
                notFound() :
                ok(motorcycleById);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ok(_motorcycleService.countMotorcyclesInStock());
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody Motorcycle motorcycle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _motorcycleService.saveMotorcycle(motorcycle);
        return created();
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> put(@Validated @RequestBody Motorcycle motorcycle,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        Motorcycle motorcycleById = _motorcycleService.getMotorcycleById(id);
        if (motorcycleById == null) {
            return notFound();
        } else if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        motorcycle.setId(id);
        _motorcycleService.saveMotorcycle(motorcycle);

        return noContent();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Motorcycle motorcycleFromCatalogueById = _motorcycleService.getMotorcycleById(id);
        if (motorcycleFromCatalogueById != null) {
            _motorcycleService.removeMotorcycleFromCatalogue(id);
            return noContent();
        }
        return notFound();
    }
}
