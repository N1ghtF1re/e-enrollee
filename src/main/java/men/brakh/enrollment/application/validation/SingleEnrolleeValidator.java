package men.brakh.enrollment.application.validation;

import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SingleEnrolleeValidator implements ConstraintValidator<
        SingleEnrollee, UniversityApplication> {
    @Override
    public boolean isValid(final UniversityApplication universityApplication, final ConstraintValidatorContext context) {
        Enrollee enrollee = universityApplication.getEnrollee();

        return universityApplication.getEducationDocument().getEnrollee().equals(enrollee)
                && universityApplication.getCertificates().stream().allMatch(ctCertificate ->
                                                                ctCertificate.getEnrollee().equals(enrollee));
    }
}
