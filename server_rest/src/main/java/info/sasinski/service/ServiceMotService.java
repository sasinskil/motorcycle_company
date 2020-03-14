package info.sasinski.service;

import info.sasinski.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class ServiceMotService {

    final ServiceRepository _serviceRepository;
    final MotorcycleDetailsService _motorcycleDetailsService;

    public List<info.sasinski.entity.Service> getAll() {
        return _serviceRepository.findAll();
    }

    public long countServices() { return _serviceRepository.count(); }

    public void saveService(info.sasinski.entity.Service service) {
        boolean checkIsSold = service.getMotorcycleDetails().getIsSold();

        if(checkIsSold) {
            _serviceRepository.save(service);
        }
    }

    public info.sasinski.entity.Service getById(long id) {
        return _serviceRepository
                .findById(id)
                .orElse(null);
    }

    public void removeService(long id) {
        _serviceRepository.deleteById(id);
    }
}

