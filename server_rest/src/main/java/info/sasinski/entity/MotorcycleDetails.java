package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "motorcycle_details")
public class MotorcycleDetails implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_motorcycle_details", sequenceName = "seq_motorcycle_details", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_motorcycle_details")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Column(precision = 10, scale = 3)
    @NotNull
    BigDecimal price;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "motorcycle_id")
    @NotNull
    Motorcycle motorcycle;

    @OneToOne(mappedBy = "motorcycleDetails")
    @JsonIgnore
    Transaction transaction;

    @OneToMany(mappedBy = "motorcycleDetails")
    @Column(name = "test_drive")
    @JsonIgnore
    List<TestDrive> testDrive = new ArrayList<>();
}
