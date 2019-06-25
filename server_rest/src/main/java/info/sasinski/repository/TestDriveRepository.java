package info.sasinski.repository;


import info.sasinski.entity.TestDrive;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestDriveRepository extends CrudRepository<TestDrive, Long> {

    @Query("SELECT TD FROM TestDrive TD")
    List<TestDrive> getAllTD();

}
