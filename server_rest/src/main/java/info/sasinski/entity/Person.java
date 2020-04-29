package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "persons")
public abstract class Person implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_persons", sequenceName = "seq_persons", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_persons")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 2, max = 20,message = "first name must be between {min} and {max}")
    String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 20,message = "last name must be between {min} and {max}")
    String lastName;

    @Past
    @NotNull
    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @NotNull(message = "address must appear")
    Address address;
}
