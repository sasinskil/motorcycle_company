package info.sasinski.service;

import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.repository.MotorcycleDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class MotorcycleDetailsService {

    final MotorcycleDetailsRepository _motorcycleDetailsRepository;

    public List<MotorcycleDetails> allMotorcyclesInStock() {
        return _motorcycleDetailsRepository.findAll();
    }

    public long countMotorcyclesInStock() {
        return _motorcycleDetailsRepository.count();
    }

    public List<MotorcycleDetails> findMotorcycleWithHighestPrice() {
        return _motorcycleDetailsRepository.findAll();
    }

    public MotorcycleDetails findMotorcycleDetailsById(long id) {
        return _motorcycleDetailsRepository.findById(id).orElse(null);
    }

    public void removeSimpleMotorcycle(long id) {
        _motorcycleDetailsRepository.deleteById(id);
    }

    public void saveSimpleMotorcycle(MotorcycleDetails motorcycleDetails) {
        _motorcycleDetailsRepository.save(motorcycleDetails);
    }
}
