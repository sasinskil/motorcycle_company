package info.sasinski.repository;

import info.sasinski.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    List<Motorcycle> findAllByBrand(@NotNull String brand);

    List<Motorcycle> findAllByPowerGreaterThan(@NotNull Integer power);
}
