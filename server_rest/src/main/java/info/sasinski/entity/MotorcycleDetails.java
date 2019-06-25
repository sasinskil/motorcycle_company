package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "motorcycle_details")
public class MotorcycleDetails {
    @Id
    @SequenceGenerator(name = "MotorcycleDetails_generator", sequenceName = "MotorcycleDetails_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MotorcycleDetails_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(precision = 10, scale = 3)
    @NotNull
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Motorcycle_Id")
    @NotNull
    Motorcycle motorcycle;

    @OneToOne(mappedBy = "motorcycleDetails")
    @JsonIgnore
    private Transaction transaction;
    @OneToMany(mappedBy = "motorcycleDetails")
    @Column(name = "Test_Drive", nullable = true)
    @JsonIgnore
    private List<TestDrive> testDrive;


    public MotorcycleDetails() {
    }

    public MotorcycleDetails(BigDecimal price, Motorcycle motorcycle, Transaction transaction,
                             List<TestDrive> testDrive) {

        this.price = price;
        this.motorcycle = motorcycle;
        this.transaction = transaction;
        this.testDrive = testDrive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<TestDrive> getTestDrive() {
        return testDrive;
    }

    public void setTestDrive(List<TestDrive> testDrive) {
        this.testDrive = testDrive;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((motorcycle == null) ? 0 : motorcycle.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((testDrive == null) ? 0 : testDrive.hashCode());
        result = prime * result + ((transaction == null) ? 0 : transaction.hashCode());
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
        MotorcycleDetails other = (MotorcycleDetails) obj;
        if (motorcycle == null) {
            if (other.motorcycle != null)
                return false;
        } else if (!motorcycle.equals(other.motorcycle))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
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
        return "MotorcycleDetails [id=" + id + ", price=" + price + ", motorcycle=" + motorcycle + ", transaction="
                + transaction + ", testDrive=" + testDrive + "]";
    }


}
