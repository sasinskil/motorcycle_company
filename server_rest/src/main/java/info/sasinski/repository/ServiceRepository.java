package info.sasinski.repository;


import info.sasinski.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT S FROM Service S")
    List<Service> getAllS();

}
