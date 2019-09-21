package men.brakh.abiturient.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;

public class NotFutureYearValidator implements ConstraintValidator<NotFutureYear, Integer> {
    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        return value <= currentYear;
    }
}
