package info.sasinski.transfer.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class RegisterRequest {

    @NotBlank
    @Size(min = 6, max = 50)
    final String name;

    @NotBlank
    @Size(min = 6, max = 50)
    final String username;

    @NotBlank
    @Size(max = 60)
    @Email
    final String email;

    final Set<String> role = new HashSet<>();

    @NotBlank
    @Size(min = 6, max = 40)
    final String password;
}
