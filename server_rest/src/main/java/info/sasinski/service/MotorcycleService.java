package info.sasinski.service;

import info.sasinski.entity.Motorcycle;
import info.sasinski.repository.MotorcycleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class MotorcycleService {

    final MotorcycleRepository _motorcycleRepository;

    public Motorcycle getMotorcycleById(long id) {
        return _motorcycleRepository.findById(id).orElse(null);
    }

    public List<Motorcycle> allMotorcyclesInStock() {
        return _motorcycleRepository.findAll();
    }

    public long countMotorcyclesInStock() {
        return _motorcycleRepository.count();
    }

    public void removeMotorcycleFromCatalogue(long id) {
        _motorcycleRepository.deleteById(id);
    }

    public void saveMotorcycle(Motorcycle motorcycle) {
        _motorcycleRepository.save(motorcycle);
    }
}
