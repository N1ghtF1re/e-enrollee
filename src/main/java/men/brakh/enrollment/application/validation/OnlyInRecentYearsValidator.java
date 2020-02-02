package men.brakh.enrollment.application.validation;

import men.brakh.enrollment.domain.ctCertificate.CtCertificate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.List;

public class OnlyInRecentYearsValidator implements ConstraintValidator<
        OnlyInRecentYears, List<CtCertificate>> {

    private int yearsCount;

    @Override
    public void initialize(final OnlyInRecentYears constraintAnnotation) {
        yearsCount = constraintAnnotation.years();
    }

    @Override
    public boolean isValid(final List<CtCertificate> value, final ConstraintValidatorContext context) {
        if (value == null) return true;

        return value.stream()
                .allMatch(ctCertificate -> {
                    final int certYear = ctCertificate.getYear();
                    final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    return certYear > currentYear - yearsCount;
                });
    }
}
