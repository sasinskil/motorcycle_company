package info.sasinski.repository;

import info.sasinski.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    Motorcycle getMotorcycleById(long id);

    @Query("SELECT M FROM Motorcycle M")
    List<Motorcycle> allMotorcyclesInStock();

    @Query("SELECT M FROM Motorcycle M WHERE M.brand = :brand")
    List<Motorcycle> findByBrand(@Param("brand") String brand);

    @Query("SELECT M FROM Motorcycle M WHERE M.power > :pow")
    List<Motorcycle> powerGreaterThan(@Param("pow") int pow);

    @Query("SELECT COUNT(M.id) FROM Motorcycle M")
    int countMotorcyclesInStock();

}
