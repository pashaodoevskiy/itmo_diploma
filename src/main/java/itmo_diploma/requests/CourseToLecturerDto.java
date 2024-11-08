package itmo_diploma.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseToLecturerDto {
    @NotNull(message = "Поле обязательно для заполнения")
    @Max(value = 10000000000000000L)
    Long lecturerId;

    @NotNull(message = "Поле обязательно для заполнения")
    @Max(value = 10000000000000000L)
    Long courseId;
}
