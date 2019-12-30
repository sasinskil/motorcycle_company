package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.entity.enumeration.Classification;
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
@Table(name = "motorcycles")
public class Motorcycle implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_motorcycles", sequenceName = "seq_motorcycles", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_motorcycles")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    Classification classification;

    @NotNull
    String brand;

    @NotNull
    String model;

    @Column(name = "engine_type")
    @NotNull
    String engineType;

    @NotNull
    BigDecimal capacity;

    @NotNull
    Integer power;

    @NotNull
    BigDecimal weight;

    @OneToMany(mappedBy = "motorcycle", fetch = FetchType.EAGER)
    @Column(name = "motorcycle_details")
    @JsonIgnore
    List<MotorcycleDetails> motorcycleDetails = new ArrayList<>();
}
