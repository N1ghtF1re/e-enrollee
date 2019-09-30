package men.brakh.enrollment.model.universityApplication.mapping;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.model.universityApplication.EducationType;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UniversityApplicationDtoMapperTest {
    private UniversityApplicationDtoMapper universityApplicationDtoMapper;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    private  Enrollee enrollee;

    private CtCertificate ctCertificate;

    private EducationDocument educationDocument;

    private CtCertificateDto ctCertificateDto;

    private EducationDocumentDto educationDocumentDto;

    private EnrolleeDto enrolleeDto;

    @Before
    public void startUp() throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        enrollee = Enrollee.builder()
                .id(1)
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        enrolleeDto = EnrolleeDto.builder()
                .id(1)
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        ctCertificate = CtCertificate.builder()
                .enrollee(enrollee)
                .id(1)
                .certificateIdentifier("11-111-1")
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(Subject.HISTORY)
                .build();

        ctCertificateDto = CtCertificateDto.builder()
                .enrolleeId(enrollee.getId())
                .id(1)
                .certificateId("11-111-1")
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(Subject.HISTORY.getSubjectName())
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
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        EnrolleeRepository enrolleeRepository = Mockito.mock(EnrolleeRepository.class);
        when(enrolleeRepository.findById(any())).thenReturn(Optional.of(enrollee));

        EducationDocumentRepository educationDocumentRepository = Mockito.mock(EducationDocumentRepository.class);
        when(educationDocumentRepository.findById(any())).thenReturn(Optional.of(educationDocument));

        CtCertificateRepository ctCertificateRepository = Mockito.mock(CtCertificateRepository.class);
        when(ctCertificateRepository.findById(any())).thenReturn(Optional.of(ctCertificate));



        universityApplicationDtoMapper = new UniversityApplicationDtoMapper(modelMapper, simpleDateFormat,
                enrolleeRepository,
                ctCertificateRepository,
                educationDocumentRepository);

    }


    @Test
    public void mapToEntity() throws ParseException {
        UniversityApplicationDto universityApplicationDto = UniversityApplicationDto.builder()
                .enrolleeId(1)
                .enrolleeName("A")
                .certificates(Collections.singletonList(ctCertificateDto))
                .educationDocument(educationDocumentDto)
                .specialities(Arrays.asList("EEB", "POIT"))
                .date("10.10.2010")
                .type(EducationType.PAID_EDUCATION.toString())
                .build();

        UniversityApplication universityApplication = UniversityApplication.builder()
                .enrollee(enrollee)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .type(EducationType.PAID_EDUCATION)
                .date(simpleDateFormat.parse("10.10.2010"))
                .build();

        assertEquals(universityApplication, universityApplicationDtoMapper.mapToEntity(universityApplicationDto));
    }

    @Test
    public void mapToEntity1() {
        UniversityApplicationCreateRequest universityApplicationCreateRequest = UniversityApplicationCreateRequest.builder()
                .enrolleeId(enrollee.getId())
                .certificateIdsList(Collections.singletonList(1))
                .educationDocumentId(1)
                .specialities(Arrays.asList("EEB", "POIT"))
                .type(EducationType.FREE_EDUCATION.toString())
                .build();

        UniversityApplication created = universityApplicationDtoMapper.mapToEntity(universityApplicationCreateRequest);

        UniversityApplication universityApplication = UniversityApplication.builder()
                .enrollee(enrollee)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .type(EducationType.FREE_EDUCATION)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(created.getDate())
                .build();

        assertEquals(universityApplication, created);

    }

    @Test
    public void mapToEntity2() {
        UniversityApplication universityApplication = UniversityApplication.builder()
                .date(new Date())
                .enrollee(enrollee)
                .build();

        UniversityApplicationUpdateRequest universityApplicationUpdateRequest = UniversityApplicationUpdateRequest.builder()
                .certificateIdsList(Collections.singletonList(1))
                .educationDocumentId(1)
                .specialities(Arrays.asList("EEB", "POIT"))
                .build();

        UniversityApplication expected = UniversityApplication.builder()
                .enrollee(enrollee)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(universityApplication.getDate())
                .build();

        assertEquals(expected, universityApplicationDtoMapper.mapToEntity(universityApplication, universityApplicationUpdateRequest));
    }
}