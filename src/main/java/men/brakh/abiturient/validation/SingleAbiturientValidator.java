package men.brakh.abiturient.validation;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.statement.Statement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SingleAbiturientValidator implements ConstraintValidator<
        SingleAbutirient, Statement> {
    @Override
    public boolean isValid(final Statement statement, final ConstraintValidatorContext context) {
        Abiturient abiturient = statement.getAbiturient();

        return statement.getEducationDocument().getAbiturient().equals(abiturient)
                && statement.getCertificates().stream().allMatch(ctCertificate ->
                                                                ctCertificate.getAbiturient().equals(abiturient));
    }
}
