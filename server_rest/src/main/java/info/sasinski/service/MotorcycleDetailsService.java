package info.sasinski.service;

import info.sasinski.entity.MotorcycleDetails;

import java.util.List;

public interface MotorcycleDetailsService {

    List<MotorcycleDetails> allMotorcyclesInStock();

    int countMotorcyclesInStock();

    List<MotorcycleDetails> findMotorcycleWithHighestPrice();

    MotorcycleDetails findMotorcycleDetailsById(long id);

    void removeSimpleMotorcycle(long id);

    void saveSimpleMotorcycle(MotorcycleDetails motorcycleDetails);


}
