package itmo_diploma.validation.annotations;

import itmo_diploma.validation.WholeNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WholeNumberValidator.class)
public @interface WholeNumber {
    String message() default "Поле обязательно для заполнения";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
