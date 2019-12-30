package info.sasinski.transfer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class LoginResponse {

    final String token;
    final String type = "Bearer";
    final String username;
    final Collection<? extends GrantedAuthority> authorities;
}
