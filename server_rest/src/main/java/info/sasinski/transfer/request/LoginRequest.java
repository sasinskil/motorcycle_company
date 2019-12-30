package info.sasinski.transfer.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
public class LoginRequest {

    @NotBlank
    @Size(min = 6, max = 60)
    final String username;

    @NotBlank
    @Size(min = 6, max = 40)
    final String password;
}
