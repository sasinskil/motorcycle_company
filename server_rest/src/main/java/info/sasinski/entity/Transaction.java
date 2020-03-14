package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_transactions", sequenceName = "seq_transactions", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_transactions")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Column
    @NotNull
    @Size(min = 1, max = 20, message = "operation must be between {min} and {max}")
    String operation;

    @Column(precision = 8, scale = 3)
    @NotNull
    BigDecimal price;

    @Column(name = "transaction_time")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime transactionTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @NotNull
    Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @NotNull
    Employee employee;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorcycle_details_id")
    @NotNull
    MotorcycleDetails motorcycleDetails;
}
