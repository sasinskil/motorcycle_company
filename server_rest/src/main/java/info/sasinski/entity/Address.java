package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Address{

    @Id
    @SequenceGenerator(name = "Address_generator", sequenceName = "Address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Address_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotNull
    @Size(min = 4, max = 20,message = "locality size must be between {min} and {max}")
    protected String locality;
    @Column(name = "Zip_code")
    @NotNull
    @Pattern(regexp = "\\d{2}-\\d{3}",message = "locality zip code must contain pattern " + "\\d{2}-\\d{3}")
    protected String zipCode;
    @NotNull
    @Size(min = 3, max = 20,message = "street size must be between {min} and {max}")
    protected String street;
    @Column(name = "Street_number")
    @Min(value = 1,message = "street number must be greater than or equal to {value}")
    protected int streetNumber;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    protected Person person;

    public Address() {
    }

    public Address(String locality, String zipCode, String street, int streetNumber) {
        this.locality = locality;
        this.zipCode = zipCode;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((locality == null) ? 0 : locality.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + streetNumber;
        result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
        Address other = (Address) obj;
        if (locality == null) {
            if (other.locality != null)
                return false;
        } else if (!locality.equals(other.locality))
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (streetNumber != other.streetNumber)
            return false;
        if (zipCode == null) {
            if (other.zipCode != null)
                return false;
        } else if (!zipCode.equals(other.zipCode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address [locality=" + locality + ", zipCode=" + zipCode + ", street=" + street + ", streetNumber="
                + streetNumber + "]";
    }


}