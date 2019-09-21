package men.brakh.abiturient.model.statement.mapping;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.model.statement.dto.StatementCreateRequest;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import men.brakh.abiturient.model.statement.dto.StatementUpdateRequest;
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

public class StatementDtoMapperTest {
    private StatementDtoMapper statementDtoMapper;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    private  Abiturient abiturient;

    private CtCertificate ctCertificate;

    private EducationDocument educationDocument;

    private CtCertificateDto ctCertificateDto;

    private EducationDocumentDto educationDocumentDto;

    private AbiturientDto abiturientDto;

    @Before
    public void startUp() throws ParseException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        abiturient = Abiturient.builder()
                .id(1)
                .birthDate(simpleDateFormat.parse("19.08.2000"))
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        abiturientDto = AbiturientDto.builder()
                .id(1)
                .birthDate("19.08.2000")
                .firstName("Alexander")
                .lastName("Pankratiev")
                .middleName("Sergeevich")
                .build();

        ctCertificate = CtCertificate.builder()
                .abiturient(abiturient)
                .id(1)
                .certificateIdentifier("11-111-1")
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(Subject.HISTORY)
                .build();

        ctCertificateDto = CtCertificateDto.builder()
                .abiturientId(abiturient.getId())
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
                .abiturient(abiturient)
                .documentUniqueNumber("A")
                .documentType("B")
                .build();


        educationDocumentDto = EducationDocumentDto.builder()
                .id(1)
                .educationalInstitution("A")
                .averageGrade(1.0)
                .abiturientId(abiturient.getId())
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        AbiturientRepository abiturientRepository = Mockito.mock(AbiturientRepository.class);
        when(abiturientRepository.findById(any())).thenReturn(Optional.of(abiturient));

        EducationDocumentRepository educationDocumentRepository = Mockito.mock(EducationDocumentRepository.class);
        when(educationDocumentRepository.findById(any())).thenReturn(Optional.of(educationDocument));

        CtCertificateRepository ctCertificateRepository = Mockito.mock(CtCertificateRepository.class);
        when(ctCertificateRepository.findById(any())).thenReturn(Optional.of(ctCertificate));



        statementDtoMapper = new StatementDtoMapper(modelMapper, simpleDateFormat,
                abiturientRepository,
                ctCertificateRepository,
                educationDocumentRepository);

    }


    @Test
    public void mapToEntity() throws ParseException {
        StatementDto statementDto = StatementDto.builder()
                .abiturientId(1)
                .abiturientName("A")
                .certificates(Collections.singletonList(ctCertificateDto))
                .educationDocument(educationDocumentDto)
                .specialities(Arrays.asList("EEB", "POIT"))
                .date("10.10.2010")
                .build();

        Statement statement = Statement.builder()
                .abiturient(abiturient)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(simpleDateFormat.parse("10.10.2010"))
                .build();

        assertEquals(statement, statementDtoMapper.mapToEntity(statementDto));
    }

    @Test
    public void mapToEntity1() {
        StatementCreateRequest statementCreateRequest = StatementCreateRequest.builder()
                .abiturientId(abiturient.getId())
                .certificateIdsList(Collections.singletonList(1))
                .educationDocumentId(1)
                .specialities(Arrays.asList("EEB", "POIT"))
                .build();

        Statement created = statementDtoMapper.mapToEntity(statementCreateRequest);

        Statement statement = Statement.builder()
                .abiturient(abiturient)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(created.getDate())
                .build();

        assertEquals(statement, created);

    }

    @Test
    public void mapToEntity2() {
        Statement statement = Statement.builder()
                .date(new Date())
                .abiturient(abiturient)
                .build();

        StatementUpdateRequest statementUpdateRequest = StatementUpdateRequest.builder()
                .certificateIdsList(Collections.singletonList(1))
                .educationDocumentId(1)
                .specialities(Arrays.asList("EEB", "POIT"))
                .build();

        Statement expected = Statement.builder()
                .abiturient(abiturient)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(statement.getDate())
                .build();

        assertEquals(expected, statementDtoMapper.mapToEntity(statement, statementUpdateRequest));
    }
}