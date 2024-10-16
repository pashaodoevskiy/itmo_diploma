package itmo_diploma.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerRequest extends Request {

    @Max(value = 10000000000000000L, groups = Update.class)
    Long id;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    String name;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле должно содержать только буквы русского алфавита")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    String surname;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Email(message = "Укажите корректный адрес электронной почты")
    String email;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Укажите корректный номер телефона")
    String phone;
}
