package men.brakh.enrollment.model.universityApplication;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.specialty.Specialty;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UniversityApplicationValidationTest {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.usingContext().getValidator();

    private static Date date = new Date();

    private Enrollee enrollee() {
        return Enrollee.builder()
                .middleName("Aa")
                .lastName("Aa")
                .firstName("Bb")
                .birthDate(date)
                .id(1)
                .build();
    }

    private CtCertificate ctCertificate(Subject subject) {
        return CtCertificate.builder()
                .enrollee(enrollee())
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
                .enrollee(enrollee())
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();
    }


    @Test
    public void test() {
        UniversityApplication universityApplication = UniversityApplication.builder()
                .id(0)
                .specialties(Arrays.asList(Specialty.POIT))
                .certificates(Arrays.asList(
                        ctCertificate(Subject.MATH),
                        ctCertificate(Subject.PHYSICS),
                        ctCertificate(Subject.BELORUSSIAN_LANG)
                ))
                .educationDocument(educationDocument())
                .type(UniversityApplicationType.FREE_EDUCATIONS_APPLICATION)
                .date(new Date())
                .enrollee(enrollee())
                .build();

        Set<ConstraintViolation<UniversityApplication>> violations = validator.validate(universityApplication);
        assertEquals(0, violations.size());
    }

    @Test
    public void test2() {
        UniversityApplication universityApplication = UniversityApplication.builder()
                .specialties(Arrays.asList(Specialty.POIT))
                .certificates(Arrays.asList(
                        ctCertificate(Subject.HISTORY),
                        ctCertificate(Subject.PHYSICS),
                        ctCertificate(Subject.BELORUSSIAN_LANG)
                ))
                .educationDocument(educationDocument())
                .type(UniversityApplicationType.FREE_EDUCATIONS_APPLICATION)
                .date(new Date())
                .enrollee(enrollee())
                .build();

        Set<ConstraintViolation<UniversityApplication>> violations = validator.validate(universityApplication);
        assertEquals(1, violations.size());
    }
}