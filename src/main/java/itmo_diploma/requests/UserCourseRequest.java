package itmo_diploma.requests;

import itmo_diploma.enums.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCourseRequest extends Request {
    @NotNull(message = "Поле обязательно для заполнения")
    @Max(value = 10000000000000000L)
    Long courseId;

    @NotNull(groups = Save.class, message = "Поле обязательно для заполнения")
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;
}