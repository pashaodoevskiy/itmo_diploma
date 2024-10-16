package itmo_diploma.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest extends Request {

    @Max(value = 10000000000000000L, groups = Update.class)
    Long id;

    @NotBlank(groups = Save.class, message = "Имя пользователя не может быть пустыми")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    private String username;

    @NotBlank(groups = Save.class, message = "Поле обязательно для заполнения")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    String name;

    @NotBlank(groups = Save.class, message = "Поле обязательно для заполнения")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    String surname;

    @NotBlank(groups = Save.class, message = "Адрес электронной почты не может быть пустыми")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @NotBlank(groups = Save.class, message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Укажите корректный номер телефона")
    String phone;
}
