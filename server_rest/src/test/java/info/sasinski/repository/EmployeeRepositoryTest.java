package info.sasinski.repository;


import info.sasinski.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;


    @Test
    public void findAllByDateOfEmployment() {
        List<Employee> result = employeeRepository.findAllByDateOfEmployment(LocalDate.of(2013, Month.FEBRUARY, 20));

        assertEquals(1, result.size());
    }

    @Test
    public void findAllEmployees()
    {
        List<Employee> findAll = employeeRepository.findAll();

        assertEquals(6,findAll.size());
    }

}
