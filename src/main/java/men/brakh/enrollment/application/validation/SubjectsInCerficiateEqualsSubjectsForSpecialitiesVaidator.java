package men.brakh.enrollment.application.validation;

import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectsInCerficiateEqualsSubjectsForSpecialitiesVaidator implements ConstraintValidator<
        SubjectsInCerficiateEqualsSubjectsForSpecialities, UniversityApplication> {
    @Override
    public boolean isValid(final UniversityApplication universityApplication, final ConstraintValidatorContext context) {
        Set<Subject> ctSubjects = universityApplication.getCertificates()
                .stream()
                .map(CtCertificate::getSubject)
                .collect(Collectors.toSet());


        return universityApplication.getSpecialties()
                .stream()
                .allMatch(
                        specialty -> specialty.getAllowedSubjects().containsAll(ctSubjects)

                );

    }


}
