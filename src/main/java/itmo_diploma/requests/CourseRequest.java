package itmo_diploma.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import itmo_diploma.validation.annotations.TodayOrFutureDate;
import itmo_diploma.validation.annotations.WholeNumber;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseRequest extends Request {

    @Max(value = 10000000000000000L, groups = Update.class)
    Long id;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[а-яА-Я0-9]+$", message = "Поле должно содержать только буквы русского алфавита")
    @Size(min = 3, max = 20, message = "Поле должно быть длиной от 3 до 20 символов.")
    String name;

    @WholeNumber(groups = Save.class)
    @Digits(integer = 6, fraction = 0, message = "Поле должно быть числом от 1 до 999999")
    Integer price;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @TodayOrFutureDate(groups = Save.class)
    LocalDate startDate;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @TodayOrFutureDate(groups = Save.class)
    LocalDate endDate;

    LecturerRequest lecturer;
}
