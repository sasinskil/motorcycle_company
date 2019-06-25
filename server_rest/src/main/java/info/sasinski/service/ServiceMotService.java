package info.sasinski.service;

import info.sasinski.entity.Service;

import java.util.List;

public interface ServiceMotService {

    List<Service> getAllS();

    void saveService(Service service);
}
