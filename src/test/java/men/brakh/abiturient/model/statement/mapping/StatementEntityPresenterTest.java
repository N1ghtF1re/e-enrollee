package men.brakh.abiturient.model.statement.mapping;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class StatementEntityPresenterTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private StatementEntityPresenter entityPresenter;

    private Abiturient abiturient;

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

        entityPresenter = new StatementEntityPresenter(
                modelMapper, simpleDateFormat,
                new CtCertificateEntityPresenter(modelMapper),
                new EducationDocumentEntityPresenter(modelMapper)
        );

        abiturient = Abiturient.builder()
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
                .abiturient(abiturient)
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        educationDocumentDto = EducationDocumentDto.builder()
                .id(1)
                .educationalInstitution("A")
                .averageGrade(1.0)
                .abiturientId(abiturient.getId())
                .abiturientName(abiturient.getFullName())
                .documentUniqueNumber("A")
                .documentType("B")
                .build();

        ctCertificate = CtCertificate.builder()
                .abiturient(abiturient)
                .id(1)
                .certificateIdentifier("11-111-1")
                .ctPoints(11)
                .certificateNumber("1111111")
                .subject(Subject.HISTORY)
                .year(2018)
                .build();

        ctCertificateDto = CtCertificateDto.builder()
                .abiturientId(abiturient.getId())
                .id(1)
                .certificateId("11-111-1")
                .ctPoints(11)
                .abiturientId(abiturient.getId())
                .abiturientName(abiturient.getFullName())
                .certificateNumber("1111111")
                .year(2018)
                .subject(Subject.HISTORY.getSubjectName())
                .build();



    }

    @Test
    public void mapToDto() throws ParseException {

        Statement statement = Statement.builder()
                .abiturient(abiturient)
                .certificates(Collections.singletonList(ctCertificate))
                .educationDocument(educationDocument)
                .specialties(Arrays.asList(Specialty.EEB, Specialty.POIT))
                .date(simpleDateFormat.parse("10.10.2010"))
                .build();

        StatementDto statementDto = StatementDto.builder()
                .abiturientId(1)
                .abiturientName(abiturient.getFullName())
                .certificates(Collections.singletonList(ctCertificateDto))
                .educationDocument(educationDocumentDto)
                .specialities(Arrays.asList("EEB", "POIT"))
                .date("10.10.2010")
                .build();

        assertEquals(statementDto, entityPresenter.mapToDto(statement, StatementDto.class));
    }
}