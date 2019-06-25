package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.entity.enumeration.Gender;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Customer extends Person {

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TestDrive> testDrive;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transaction;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Service> service;

    public Customer() {
    }

    public Customer(String firstName, String lastName, Gender gender, LocalDate birthDate,
                    Address address) {
        super(firstName, lastName, gender, birthDate, address);

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
        Customer other = (Customer) obj;
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
        String customerS = super.toString();
        return customerS;
    }

}
