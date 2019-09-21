package men.brakh.abiturient.model.statement;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.specialty.Specialty;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StatementValidationTest {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.usingContext().getValidator();

    private static Date date = new Date();

    private Abiturient abiturient() {
        return Abiturient.builder()
                .middleName("Aa")
                .lastName("Aa")
                .firstName("Bb")
                .birthDate(date)
                .id(1)
                .build();
    }

    private CtCertificate ctCertificate(Subject subject) {
        return CtCertificate.builder()
                .abiturient(abiturient())
                .id(1)
                .certificateIdentifier("11-111-1")
                .year(2019)
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(subject)
                .build();
    }

    private EducationDocument educationDocument() {
        return EducationDocument.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturient(abiturient())
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();
    }


    @Test
    public void test() {
        Statement statement = Statement.builder()
                .id(0)
                .specialties(Arrays.asList(Specialty.POIT))
                .certificates(Arrays.asList(
                        ctCertificate(Subject.MATH),
                        ctCertificate(Subject.PHYSICS),
                        ctCertificate(Subject.BELORUSSIAN_LANG)
                ))
                .educationDocument(educationDocument())
                .date(new Date())
                .abiturient(abiturient())
                .build();

        Set<ConstraintViolation<Statement>> violations = validator.validate(statement);
        assertEquals(0, violations.size());
    }

    @Test
    public void test2() {
        Statement statement = Statement.builder()
                .specialties(Arrays.asList(Specialty.POIT))
                .certificates(Arrays.asList(
                        ctCertificate(Subject.HISTORY),
                        ctCertificate(Subject.PHYSICS),
                        ctCertificate(Subject.BELORUSSIAN_LANG)
                ))
                .educationDocument(educationDocument())
                .date(new Date())
                .abiturient(abiturient())
                .build();

        Set<ConstraintViolation<Statement>> violations = validator.validate(statement);
        assertEquals(1, violations.size());
    }
}