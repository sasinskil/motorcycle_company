package info.sasinski.service.impl;

import info.sasinski.entity.Motorcycle;
import info.sasinski.repository.MotorcycleRepository;
import info.sasinski.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    @Autowired
    public MotorcycleServiceImpl(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    @Override
    public Motorcycle getMotorcycleById(long id) {
        return motorcycleRepository.getMotorcycleById(id);
    }

    @Override
    public List<Motorcycle> allMotorcyclesInStock() {
        return motorcycleRepository.allMotorcyclesInStock();
    }

    @Override
    public List<Motorcycle> findByBrand(String brand) {
        return motorcycleRepository.findByBrand(brand);
    }

    @Override
    public List<Motorcycle> powerGreaterThan(int pow) {
        return motorcycleRepository.powerGreaterThan(pow);
    }

    @Override
    public int countMotorcyclesInStock() {
        return motorcycleRepository.countMotorcyclesInStock();
    }

    @Override
    public void saveMotorcycle(Motorcycle motorcycle) {
        motorcycleRepository.save(motorcycle);
    }
}
