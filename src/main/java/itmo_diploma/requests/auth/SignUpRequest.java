package itmo_diploma.requests.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpRequest {

    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;

    @NotBlank(message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    String name;

    @NotBlank(message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    String surname;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @NotBlank(message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Укажите корректный номер телефона")
    String phone;
}
