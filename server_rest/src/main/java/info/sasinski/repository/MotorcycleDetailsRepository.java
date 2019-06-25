package info.sasinski.repository;

import info.sasinski.entity.MotorcycleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MotorcycleDetailsRepository extends JpaRepository<MotorcycleDetails, Long> {

    @Query("SELECT MD FROM MotorcycleDetails MD")
    List<MotorcycleDetails> allMotorcyclesInStock();

    @Query("SELECT COUNT(MD.id) FROM MotorcycleDetails MD")
    int countMotorcyclesInStock();

    @Query("SELECT MD FROM MotorcycleDetails MD  WHERE MD.price=(SELECT MAX(M.price)FROM MotorcycleDetails M)")
    List<MotorcycleDetails> findMotorcycleWithHighestPrice();

    MotorcycleDetails findMotorcycleDetailsById(long id);

}
