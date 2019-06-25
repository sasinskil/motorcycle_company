package info.sasinski.service.impl;

import info.sasinski.entity.MotorcycleDetails;
import info.sasinski.repository.MotorcycleDetailsRepository;
import info.sasinski.service.MotorcycleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleDetailsServiceImpl implements MotorcycleDetailsService {

    private final MotorcycleDetailsRepository motorcycleDetailsRepository;

    @Autowired
    public MotorcycleDetailsServiceImpl(MotorcycleDetailsRepository motorcycleDetailsRepository) {
        this.motorcycleDetailsRepository = motorcycleDetailsRepository;
    }

    @Override
    public List<MotorcycleDetails> allMotorcyclesInStock() {
        return motorcycleDetailsRepository.allMotorcyclesInStock();
    }

    @Override
    public int countMotorcyclesInStock() {
        return motorcycleDetailsRepository.countMotorcyclesInStock();
    }

    @Override
    public List<MotorcycleDetails> findMotorcycleWithHighestPrice() {
        return motorcycleDetailsRepository.findMotorcycleWithHighestPrice();
    }

    @Override
    public MotorcycleDetails findMotorcycleDetailsById(long id) {
        return motorcycleDetailsRepository.findMotorcycleDetailsById(id);
    }

    @Override
    public void removeSimpleMotorcycle(long id) {
        motorcycleDetailsRepository.deleteById(id);
    }

    @Override
    public void saveSimpleMotorcycle(MotorcycleDetails motorcycleDetails) {
        motorcycleDetailsRepository.save(motorcycleDetails);
    }
}
