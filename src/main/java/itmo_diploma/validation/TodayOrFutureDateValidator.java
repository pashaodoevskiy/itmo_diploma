package itmo_diploma.validation;

import itmo_diploma.validation.annotations.TodayOrFutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class TodayOrFutureDateValidator implements ConstraintValidator<TodayOrFutureDate, LocalDate> {

    @Override
    public void initialize(TodayOrFutureDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Поле обязательно для заполнения")
                    .addConstraintViolation();

            return false;
        }

        return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
    }
}
