package men.brakh.enrollment.validation;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SingleEnrolleeValidator implements ConstraintValidator<
        SingleAbutirient, UniversityApplication> {
    @Override
    public boolean isValid(final UniversityApplication universityApplication, final ConstraintValidatorContext context) {
        Enrollee enrollee = universityApplication.getEnrollee();

        return universityApplication.getEducationDocument().getEnrollee().equals(enrollee)
                && universityApplication.getCertificates().stream().allMatch(ctCertificate ->
                                                                ctCertificate.getEnrollee().equals(enrollee));
    }
}
