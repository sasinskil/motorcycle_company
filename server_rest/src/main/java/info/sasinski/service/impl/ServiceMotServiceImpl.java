package info.sasinski.service.impl;

import info.sasinski.repository.ServiceRepository;
import info.sasinski.service.ServiceMotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMotServiceImpl implements ServiceMotService {

    private final ServiceRepository serviceRepository;

    public ServiceMotServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<info.sasinski.entity.Service> getAllS() {
        return serviceRepository.getAllS();
    }

    @Override
    public void saveService(info.sasinski.entity.Service service) {
        serviceRepository.save(service);

    }
}
