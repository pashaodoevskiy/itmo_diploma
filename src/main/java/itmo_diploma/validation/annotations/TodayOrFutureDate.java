package itmo_diploma.validation.annotations;

import itmo_diploma.validation.TodayOrFutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TodayOrFutureDateValidator.class)
public @interface TodayOrFutureDate {
    String message() default "Дата должна быть равна или больше сегодняшнего дня";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
