package info.sasinski.controller;

import info.sasinski.entity.Motorcycle;
import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.service.MotorcycleDetailsService;
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
@RequestMapping(value = "/api/motorcycleDetails")
public class MotorcycleDetailsController extends ControllerBase {

    final MotorcycleDetailsService _motorcycleDetailsService;

    @GetMapping
    public ResponseEntity<List<MotorcycleDetails>> get() {
        List<MotorcycleDetails> motorcycleDetails = _motorcycleDetailsService.allMotorcyclesInStock();
        return motorcycleDetails.isEmpty() ?
                notFound() :
                ok(motorcycleDetails);
    }

    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<MotorcycleDetails> details(@PathVariable("id") long id) {
        MotorcycleDetails motorcycleDetails = _motorcycleDetailsService.findMotorcycleDetailsById(id);
        return motorcycleDetails == null ?
                notFound() :
                ok(motorcycleDetails);
    }

    @GetMapping("/{id:\\d+}/motorcycle")
    public ResponseEntity<Motorcycle> get(@PathVariable("id") long id) {
        Motorcycle motorcycle = _motorcycleDetailsService.findMotorcycleDetailsById(id).getMotorcycle();
        return motorcycle == null ?
                notFound() :
                ok(motorcycle);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ok(_motorcycleDetailsService.countMotorcyclesInStock());
    }

    @GetMapping("/highestPrice")
    public ResponseEntity<List<MotorcycleDetails>> highestPrice() {
        List<MotorcycleDetails> byPrice = _motorcycleDetailsService.findMotorcycleWithHighestPrice();
        return byPrice.isEmpty() ?
                notFound() :
                ok(byPrice);
    }

    @PostMapping
    public ResponseEntity<?> post(@Validated @RequestBody MotorcycleDetails motorcycleDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        _motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);
        return created();
    }

    @PutMapping("/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> put(@Validated @RequestBody MotorcycleDetails motorcycleDetails,
                                 BindingResult bindingResult,
                                 @PathVariable("id") long id) {

        MotorcycleDetails motorcycleDetailsById = _motorcycleDetailsService.findMotorcycleDetailsById(id);
        if (motorcycleDetailsById == null) {
            return notFound();
        } else if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return conflict(new ConstraintViolationsResponse("409", "Validation failure", errors));
        }

        motorcycleDetails.setId(id);
        _motorcycleDetailsService.saveSimpleMotorcycle(motorcycleDetails);

        return noContent();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        MotorcycleDetails motorcycleDetailsById = _motorcycleDetailsService.findMotorcycleDetailsById(id);
        if (motorcycleDetailsById != null) {
            _motorcycleDetailsService.removeSimpleMotorcycle(id);
            return noContent();
        }
        return notFound();
    }
}
