package info.sasinski.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.security.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserPrinciple implements UserDetails {

    Long id;

    String name;

    String username;

    String email;

    @JsonIgnore
    String password;

    Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String name,
                         String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    boolean isAccountNonExpired = true;

    boolean isAccountNonLocked = true;

    boolean isCredentialsNonExpired = true;

    boolean isEnabled = true;
}
