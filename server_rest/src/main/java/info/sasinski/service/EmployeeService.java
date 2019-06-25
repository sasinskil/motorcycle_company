package info.sasinski.service;

import info.sasinski.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<Employee> findAllByDateOfEmployment(LocalDate dateOfEmployment);

    int countByFirstNameIgnoreCase(String firstName);

    Employee findEmployeeById(long id);

    List<Employee> findAll();

    void removeEmployee(long id);

    void saveEmployee(Employee employee);
}
