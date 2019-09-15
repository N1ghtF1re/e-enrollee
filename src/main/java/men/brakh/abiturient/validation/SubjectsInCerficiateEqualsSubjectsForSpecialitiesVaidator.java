package men.brakh.abiturient.validation;

import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.statement.Statement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectsInCerficiateEqualsSubjectsForSpecialitiesVaidator implements ConstraintValidator<
        SubjectsInCerficiateEqualsSubjectsForSpecialities, Statement> {
    @Override
    public boolean isValid(final Statement statement, final ConstraintValidatorContext context) {
        Set<Subject> ctSubjects = statement.getCertificates()
                .stream()
                .map(CtCertificate::getSubject)
                .collect(Collectors.toSet());

        Set<Subject> specialitySubjects = statement.getSpecialties()
                .stream()
                .flatMap(specialty -> specialty.getAllowedSubjects().stream())
                .collect(Collectors.toSet());


        return specialitySubjects.containsAll(ctSubjects);
    }


}
