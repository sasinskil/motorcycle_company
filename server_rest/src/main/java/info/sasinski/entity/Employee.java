package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.entity.enumeration.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "employees")
public class Employee extends Person {

    @Column(precision = 10, scale = 3)
    BigDecimal salary;

    @Enumerated(EnumType.STRING)
    Position position;

    @Column(name = "date_of_employment")
    LocalDate dateOfEmployment;

    @Column(name = "date_of_termination")
    LocalDate dateOfTermination;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    List<TestDrive> testDrive = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Transaction> transaction = new ArrayList<>();

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Service> service = new ArrayList<>();
}
