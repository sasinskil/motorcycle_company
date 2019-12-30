package info.sasinski.service;

import info.sasinski.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class ServiceMotService {

    final ServiceRepository _serviceRepository;

    public List<info.sasinski.entity.Service> getAll() {
        return _serviceRepository.findAll();
    }

    public void saveService(info.sasinski.entity.Service service) {
        _serviceRepository.save(service);
    }

    public info.sasinski.entity.Service getById(long id) {
        return _serviceRepository
                .findById(id)
                .orElse(null);
    }
}

