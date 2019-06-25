package info.sasinski.service.impl;

import info.sasinski.entity.Employee;
import info.sasinski.repository.EmployeeRepository;
import info.sasinski.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAllByDateOfEmployment(LocalDate dateOfEmployment) {

        return employeeRepository.findAllByDateOfEmployment(dateOfEmployment);
    }

    @Override
    public int countByFirstNameIgnoreCase(String firstName) {

        return employeeRepository.countByFirstNameIgnoreCase(firstName);
    }

    @Override
    public Employee findEmployeeById(long id) {
        return employeeRepository.findEmployeeById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void removeEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
