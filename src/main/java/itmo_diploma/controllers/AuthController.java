package itmo_diploma.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo_diploma.exceptions.CustomException;
import itmo_diploma.requests.auth.SignInRequest;
import itmo_diploma.requests.auth.SignUpRequest;
import itmo_diploma.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Аутентификация")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends Controller{
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody @Valid SignUpRequest request) throws CustomException {
        return response(authenticationService.signUp(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody @Valid SignInRequest request) {
        return response(authenticationService.signIn(request));
    }
}