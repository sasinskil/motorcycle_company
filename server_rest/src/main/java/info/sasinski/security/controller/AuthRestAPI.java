package info.sasinski.security.controller;

import info.sasinski.controller.ControllerBase;
import info.sasinski.security.JwtConfig.JwtProvider;
import info.sasinski.security.model.Role;
import info.sasinski.security.model.RoleName;
import info.sasinski.security.model.User;
import info.sasinski.security.repository.RoleRepository;
import info.sasinski.security.repository.UserRepository;
import info.sasinski.transfer.request.LoginRequest;
import info.sasinski.transfer.request.RegisterRequest;
import info.sasinski.transfer.response.LoginResponse;
import info.sasinski.transfer.response.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPI extends ControllerBase {

    AuthenticationManager _authenticationManager;

    UserRepository _userRepository;

    RoleRepository _roleRepository;

    PasswordEncoder _encoder;

    JwtProvider _jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = _jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ok(new LoginResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequestRequest) {
        if (_userRepository.existsByUsername(registerRequestRequest.username())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (_userRepository.existsByEmail(registerRequestRequest.email())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setName(registerRequestRequest.name());
        user.setUsername(registerRequestRequest.username());
        user.setEmail(registerRequestRequest.email());
        user.setPassword(_encoder.encode(registerRequestRequest.password()));

        Set<String> strRoles = registerRequestRequest.role();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            if ("admin".equals(role)) {
                Role adminRole = _roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(adminRole);
            } else {
                Role userRole = _roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(userRole);
            }
        });

        user.setRoles(roles);
        _userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}
