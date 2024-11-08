package itmo_diploma.requests.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignInRequest {
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotEmpty(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotEmpty(message = "Пароль не может быть пустыми")
    private String password;
}