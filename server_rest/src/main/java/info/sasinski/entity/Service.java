package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Service implements Serializable {

    @Id
    @SequenceGenerator(name = "Service_generator", sequenceName = "Service_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Service_generator")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private long id;


    @Column
    @NotNull
    @Size(min = 1, max = 20,message = "operation must be between {min} and {max}")
    private String operation;
    @Column(precision = 5, scale = 2)
    @NotNull
    private BigDecimal price;

    @Column(name = "Start_working_date")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startWorkingDate;
    @Column(name = "End_working_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endWorkingDate;

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


    public Service() {
    }


    public Service(String operation, BigDecimal price, LocalDate startWorkingDate, LocalDate endWorkingDate,
                   Customer customer, Employee employee, MotorcycleDetails motorcycleDetails) {
        this.operation = operation;
        this.price = price;
        this.startWorkingDate = startWorkingDate;
        this.endWorkingDate = endWorkingDate;
        this.customer = customer;
        this.employee = employee;
        this.motorcycleDetails = motorcycleDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(LocalDate startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public LocalDate getEndWorkingDate() {
        return endWorkingDate;
    }

    public void setEndWorkingDate(LocalDate endWorkingDate) {
        this.endWorkingDate = endWorkingDate;
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((employee == null) ? 0 : employee.hashCode());
        result = prime * result + ((endWorkingDate == null) ? 0 : endWorkingDate.hashCode());
        result = prime * result + ((motorcycleDetails == null) ? 0 : motorcycleDetails.hashCode());
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((startWorkingDate == null) ? 0 : startWorkingDate.hashCode());
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
        Service other = (Service) obj;
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
        if (endWorkingDate == null) {
            if (other.endWorkingDate != null)
                return false;
        } else if (!endWorkingDate.equals(other.endWorkingDate))
            return false;
        if (motorcycleDetails == null) {
            if (other.motorcycleDetails != null)
                return false;
        } else if (!motorcycleDetails.equals(other.motorcycleDetails))
            return false;
        if (operation == null) {
            if (other.operation != null)
                return false;
        } else if (!operation.equals(other.operation))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (startWorkingDate == null) {
            if (other.startWorkingDate != null)
                return false;
        } else if (!startWorkingDate.equals(other.startWorkingDate))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Service [id=" + id + ", operation=" + operation + ", price=" + price + ", startWorkingDate="
                + startWorkingDate + ", endWorkingDate=" + endWorkingDate + ", customer=" + customer + ", employee="
                + employee + ", motorcycleDetails=" + motorcycleDetails + "]";
    }


}
