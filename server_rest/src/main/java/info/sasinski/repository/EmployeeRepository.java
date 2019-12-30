package info.sasinski.repository;


import info.sasinski.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByDateOfEmployment(LocalDate dateOfEmployment);

    int countByFirstNameIgnoreCase(String firstName);

}
