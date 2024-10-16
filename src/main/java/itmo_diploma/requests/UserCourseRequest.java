package itmo_diploma.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCourseRequest {
    @NotNull
    @Max(value = 10000000000000000L)
    Long courseId;
}
