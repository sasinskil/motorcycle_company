package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.sasinski.entity.enumeration.Gender;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @SequenceGenerator(name = "Person_generator", sequenceName = "Person_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Person_generator")
    @Column(name = "id", updatable = false, nullable = false)
    protected long id;

    @Column(name = "First_name")
    @NotNull
    @Pattern(regexp = "[A-Z]{1}[a-z]+",message = "first name must contain pattern " + "[A-Z]{1}[a-z]+")
    @Size(min = 2, max = 20,message = "first name must be between {min} and {max}")
    protected String firstName;
    @Column(name = "Last_name")
    @NotNull
    @Pattern(regexp = "[A-Z]{1}[a-z]+",message = "first name must contain pattern " + "[A-Z]{1}[a-z]+")
    @Size(min = 3, max = 20,message = "last name must be between {min} and {max}")
    protected String lastName;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    @Past
    @NotNull
    @Column(name = "Birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthDate;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "AddressId")
    @NotNull(message = "address must appear")
    protected Address address;

    public Person() {
    }

    public Person(String firstName, String lastName, Gender gender, LocalDate birthDate, Address address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        Person other = (Person) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (gender != other.gender)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
                + gender + ", birthDate=" + birthDate + ", address=" + address + "]";
    }

}
