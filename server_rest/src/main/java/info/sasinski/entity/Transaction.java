package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction_in")
public class Transaction {

    @Id
    @SequenceGenerator(name = "Transaction_generator", sequenceName = "Transaction_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Transaction_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column
    @NotNull
    @Size(min = 1, max = 20,message = "operation must be between {min} and {max}")
    private String operation;
    @Column(precision = 8, scale = 3)
    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Customer_Id")
    @NotNull
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Employee_Id")
    @NotNull
    private Employee employee;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Motorcycle_details_Id")
    @NotNull
    private MotorcycleDetails motorcycleDetails;

    @Column(name = "Transaction")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transaction;

    public Transaction() {
    }

    public Transaction(String operation, BigDecimal price, Customer customer, Employee employee,
                       MotorcycleDetails motorcycleDetails, LocalDateTime transaction) {

        this.operation = operation;
        this.price = price;
        this.customer = customer;
        this.employee = employee;
        this.motorcycleDetails = motorcycleDetails;
        this.transaction = transaction;

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

    public LocalDateTime getTransaction() {
        return transaction;
    }

    public void setTransaction(LocalDateTime transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(operation, that.operation) &&
                Objects.equals(price, that.price) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(motorcycleDetails, that.motorcycleDetails) &&
                Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, price, customer, employee, motorcycleDetails, transaction);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", price=" + price +
                ", customer=" + customer +
                ", employee=" + employee +
                ", motorcycleDetails=" + motorcycleDetails +
                ", transaction=" + transaction +
                '}';
    }
}
