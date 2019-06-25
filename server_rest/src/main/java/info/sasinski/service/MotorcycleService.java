package info.sasinski.service;

import info.sasinski.entity.Motorcycle;

import java.util.List;

public interface MotorcycleService {
    Motorcycle getMotorcycleById(long id);

    List<Motorcycle> allMotorcyclesInStock();

    List<Motorcycle> findByBrand(String brand);

    List<Motorcycle> powerGreaterThan(int pow);

    int countMotorcyclesInStock();

    void saveMotorcycle(Motorcycle motorcycle);
}
