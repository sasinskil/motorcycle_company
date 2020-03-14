package info.sasinski.repository;

import info.sasinski.entity.MotorcycleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MotorcycleDetailsRepository extends JpaRepository<MotorcycleDetails, Long> {
    Boolean existsByMotorcycleCode(String motorcycleCode);
    List<MotorcycleDetails> findAllByIsSoldTrue();
    List<MotorcycleDetails> findAllByIsSoldFalse();
}
