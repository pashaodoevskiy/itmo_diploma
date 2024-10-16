package itmo_diploma.validation;

import itmo_diploma.validation.annotations.WholeNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WholeNumberValidator implements ConstraintValidator<WholeNumber, Integer> {
    @Override
    public void initialize(WholeNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value > 0;
    }
}
