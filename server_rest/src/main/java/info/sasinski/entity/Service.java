package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "services")
public class Service implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_services", sequenceName = "seq_services", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_services")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    Long id;

    @Column
    @NotNull
    @Size(min = 1, max = 40,message = "operation must be between {min} and {max}")
    String operation;

    @Column(precision = 5, scale = 2)
    @NotNull
    BigDecimal price;

    @Column(name = "start_working_date")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startWorkingDate;

    @Column(name = "end_working_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endWorkingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @NotNull
    Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @NotNull
    Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorcycle_details_id")
    @NotNull
    MotorcycleDetails motorcycleDetails;
}
