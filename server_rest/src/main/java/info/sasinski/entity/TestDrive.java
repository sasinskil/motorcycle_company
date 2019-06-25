package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_drive")
public class TestDrive {

    @Id
    @SequenceGenerator(name = "TestDrive_generator", sequenceName = "TestDrive_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TestDrive_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Customer_Id")
    @NotNull
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_Id")
    @NotNull
    private Employee employee;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Motorcycle_Details_Id")
    @NotNull
    private MotorcycleDetails motorcycleDetails;

    @Column(name = "Start_Drive")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDrive;
    @Column(name = "End_Drive")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDrive;

    public TestDrive() {
    }


    public TestDrive(Customer customer, Employee employee, MotorcycleDetails motorcycleDetails,
                     LocalDateTime startDrive, LocalDateTime endDrive) {

        this.customer = customer;
        this.employee = employee;
        this.motorcycleDetails = motorcycleDetails;
        this.startDrive = startDrive;
        this.endDrive = endDrive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MotorcycleDetails getMotorcycleDetails() {
        return motorcycleDetails;
    }

    public void setMotorcycleDetails(MotorcycleDetails motorcycleDetails) {
        this.motorcycleDetails = motorcycleDetails;
    }

    public LocalDateTime getStartDrive() {
        return startDrive;
    }

    public void setStartDrive(LocalDateTime startDrive) {
        this.startDrive = startDrive;
    }

    public LocalDateTime getEndDrive() {
        return endDrive;
    }

    public void setEndDrive(LocalDateTime endDrive) {
        this.endDrive = endDrive;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((employee == null) ? 0 : employee.hashCode());
        result = prime * result + ((endDrive == null) ? 0 : endDrive.hashCode());
        result = prime * result + ((motorcycleDetails == null) ? 0 : motorcycleDetails.hashCode());
        result = prime * result + ((startDrive == null) ? 0 : startDrive.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestDrive other = (TestDrive) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (employee == null) {
            if (other.employee != null)
                return false;
        } else if (!employee.equals(other.employee))
            return false;
        if (endDrive == null) {
            if (other.endDrive != null)
                return false;
        } else if (!endDrive.equals(other.endDrive))
            return false;
        if (motorcycleDetails == null) {
            if (other.motorcycleDetails != null)
                return false;
        } else if (!motorcycleDetails.equals(other.motorcycleDetails))
            return false;
        if (startDrive == null) {
            if (other.startDrive != null)
                return false;
        } else if (!startDrive.equals(other.startDrive))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "TestDrive [id=" + id + ", customer=" + customer + ", employee=" + employee + ", motorcycleDetails="
                + motorcycleDetails.getMotorcycle() + ", startDrive=" + startDrive + ", endDrive=" + endDrive + "]";
    }


}
