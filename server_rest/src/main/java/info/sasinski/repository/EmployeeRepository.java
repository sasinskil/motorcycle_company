package info.sasinski.repository;


import info.sasinski.entity.Employee;
import info.sasinski.entity.enumeration.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByDateOfEmployment(LocalDate dateOfEmployment);

    int countByFirstNameIgnoreCase(String firstName);

    Employee findEmployeeById(long id);

}
