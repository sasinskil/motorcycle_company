package info.sasinski.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User implements Serializable {

    @Id
    @SequenceGenerator(name = "gen_users", sequenceName = "seq_users", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_users")
    @Column(updatable = false, nullable = false, unique = true)
    Long id;

    @NotBlank
    @Size(min = 3, max = 40)
    String name;

    @NotBlank
    @Size(min = 3, max = 40)
    String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    String email;

    @NotBlank
    @Size(min = 5, max = 80)
    String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();
}
