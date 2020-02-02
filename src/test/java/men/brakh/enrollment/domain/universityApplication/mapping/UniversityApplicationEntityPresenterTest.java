package men.brakh.enrollment.domain.universityApplication.mapping;

import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.domain.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.domain.specialty.Specialty;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class UniversityApplicationEntityPresenterTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private UniversityApplicationEntityPresenter entityPresenter;

    private Enrollee enrollee;

    private EducationDocument educationDocument;

    private EducationDocumentDto educationDocumentDto;

    private CtCertificate ctCertificate;

    private CtCertificateDto ctCertificateDto;


    @Before
    public void startUp() throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        entityPresenter = new UniversityApplicationEntityPresenter(
                modelMapper, simpleDateFormat,
                new CtCertificateEntityPresenter(modelMapper),
                new EducationDocumentEntityPresenter(modelMapper)
        );

        enrollee = Enrollee.builder()
                .id(1)
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        educationDocument = EducationDocument.builder()
                .id(1)
                .educationalInstitution("A")
                .averageGrade(1.0)
                .enrollee(enrollee)
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        educationDocumentDto = EducationDocumentDto.builder()
                .id(1)
                .educationalInstitution("A")
                .averageGrade(1.0)
                .enrolleeId(enrollee.getId())
                .enrolleeName(enrollee.getFullName())
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        ctCertificate = CtCertificate.builder()
                .enrollee(enrollee)
                .id(1)
                .certificateIdentifier("11-111-1")
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(Subject.HISTORY)
                .year(2018)
                .build();

        ctCertificateDto = CtCertificateDto.builder()
                .enrolleeId(enrollee.getId())
                .id(1)
                .certificateId("11-111-1")
                .ctPoints(11)
                .enrolleeId(enrollee.getId())
                .enrolleeName(enrollee.getFullName())
                .certificateNumber("1111111")
                .year(2018)
                .subject(Subject.HISTORY.getSubjectName())
                .build();



    }

    @Test
    public void mapToDto() throws ParseException {

        UniversityApplication universityApplication = UniversityApplication.builder()
                .enrollee(enrollee)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(simpleDateFormat.parse("10.10.2010"))
                .build();

        UniversityApplicationDto universityApplicationDto = UniversityApplicationDto.builder()
                .enrolleeId(1)
                .enrolleeName(enrollee.getFullName())
                .certificates(Collections.singletonList(ctCertificateDto))
                .educationDocument(educationDocumentDto)
                .specialities(Arrays.asList("EEB", "POIT"))
                .date("10.10.2010")
                .build();

        assertEquals(universityApplicationDto, entityPresenter.mapToDto(universityApplication, UniversityApplicationDto.class));
    }
}