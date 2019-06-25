package info.sasinski.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.entity.enumeration.Gender;
import info.sasinski.entity.enumeration.Position;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee extends Person {

    @Column(precision = 10, scale = 3)
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private Position position;
    @Column(name = "Date_of_employment")
    private LocalDate dateOfEmployment;
    @Column(name = "Date_of_termination")
    private LocalDate dateOfTermination;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TestDrive> testDrive;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transaction;
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Service> service;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Gender gender, LocalDate birthDate,
                    BigDecimal salary, Position position, LocalDate dateOfEmployment, LocalDate dateOfTermination, Address address) {
        super(firstName, lastName, gender, birthDate, address);
        this.salary = salary;
        this.position = position;
        this.dateOfEmployment = dateOfEmployment;
        this.dateOfTermination = dateOfTermination;


    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public LocalDate getDateOfTermination() {
        return dateOfTermination;
    }

    public void setDateOfTermination(LocalDate dateOfTermination) {
        this.dateOfTermination = dateOfTermination;
    }


    public List<TestDrive> getTestDrive() {
        return testDrive;
    }

    public void setTestDrive(List<TestDrive> testDrive) {
        this.testDrive = testDrive;
    }


    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((dateOfEmployment == null) ? 0 : dateOfEmployment.hashCode());
        result = prime * result + ((dateOfTermination == null) ? 0 : dateOfTermination.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        result = prime * result + ((testDrive == null) ? 0 : testDrive.hashCode());
        result = prime * result + ((transaction == null) ? 0 : transaction.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (dateOfEmployment == null) {
            if (other.dateOfEmployment != null)
                return false;
        } else if (!dateOfEmployment.equals(other.dateOfEmployment))
            return false;
        if (dateOfTermination == null) {
            if (other.dateOfTermination != null)
                return false;
        } else if (!dateOfTermination.equals(other.dateOfTermination))
            return false;
        if (position != other.position)
            return false;
        if (salary == null) {
            if (other.salary != null)
                return false;
        } else if (!salary.equals(other.salary))
            return false;
        if (service == null) {
            if (other.service != null)
                return false;
        } else if (!service.equals(other.service))
            return false;
        if (testDrive == null) {
            if (other.testDrive != null)
                return false;
        } else if (!testDrive.equals(other.testDrive))
            return false;
        if (transaction == null) {
            if (other.transaction != null)
                return false;
        } else if (!transaction.equals(other.transaction))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String employeeS = super.toString();
        return employeeS + "Employee [salary=" + salary + ", position=" + position + ", dateOfEmployment=" + dateOfEmployment
                + ", dateOfTermination=" + dateOfTermination;
    }


}
