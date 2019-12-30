package info.sasinski.service;

import info.sasinski.entity.Employee;
import info.sasinski.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor

@Service
public class EmployeeService {

    final EmployeeRepository _employeeRepository;

    public List<Employee> findAllByDateOfEmployment(LocalDate dateOfEmployment) {
        return _employeeRepository.findAllByDateOfEmployment(dateOfEmployment);
    }

    public int countByFirstNameIgnoreCase(String firstName) {
        return _employeeRepository.countByFirstNameIgnoreCase(firstName);
    }

    public Employee findEmployeeById(long id) {
        return _employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findAll() {
        return _employeeRepository.findAll();
    }

    public void removeEmployee(long id) {
        _employeeRepository.deleteById(id);
    }

    public void saveEmployee(Employee employee) {
        _employeeRepository.save(employee);
    }
}
