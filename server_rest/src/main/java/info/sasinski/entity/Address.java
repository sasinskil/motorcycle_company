package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_addresses", sequenceName = "seq_addresses", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_addresses")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @NotNull
    @Size(min = 4, max = 20, message = "locality size must be between {min} and {max}")
    String locality;

    @Column(name = "zip_code")
    @NotNull
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "locality zip code must contain pattern " + "\\d{2}-\\d{3}")
    String zipCode;

    @NotNull
    @Size(min = 3, max = 20, message = "street size must be between {min} and {max}")
    String street;

    @Column(name = "street_number")
    @Min(value = 1, message = "street number must be greater than or equal to {value}")
    Integer streetNumber;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    Person person;
}