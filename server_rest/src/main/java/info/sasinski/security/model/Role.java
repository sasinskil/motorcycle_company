package info.sasinski.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_roles", sequenceName = "seq_roles", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_roles")
    @Column(updatable = false, nullable = false, unique = true)
    Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 50)
    RoleName name;
}
