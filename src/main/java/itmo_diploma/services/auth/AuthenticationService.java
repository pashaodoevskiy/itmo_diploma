package itmo_diploma.services.auth;

import itmo_diploma.enums.Role;
import itmo_diploma.exceptions.CustomException;
import itmo_diploma.models.User;
import itmo_diploma.repositories.UserRepository;
import itmo_diploma.requests.auth.SignInRequest;
import itmo_diploma.requests.auth.SignUpRequest;
import itmo_diploma.responses.auth.JwtAuthenticationResponse;
import itmo_diploma.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JwtAuthenticationResponse signUp(SignUpRequest request) throws CustomException {

        if (userRepository.existsByUsernameOrEmailOrPhone(request.getUsername(), request.getEmail(), request.getPhone())) {
            throw new CustomException("Пользователь уже существует", HttpStatus.CONFLICT.value());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }
}
