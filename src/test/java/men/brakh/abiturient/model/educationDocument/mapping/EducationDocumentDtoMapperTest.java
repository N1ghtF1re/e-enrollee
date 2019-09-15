package men.brakh.abiturient.model.educationDocument.mapping;

import men.brakh.abiturient.exception.RecourseNotFoundException;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentUpdateRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public class EducationDocumentDtoMapperTest {

    private EducationDocumentDtoMapper dtoMapper;

    private Abiturient abiturient = Abiturient.builder()
            .id(1)
            .firstName("A")
            .lastName("B")
            .middleName("C")
            .build();

    private AbiturientRepository abiturientRepository = new AbiturientRepository() {
        @Override
        public Abiturient create(final Abiturient entity) {
            return null;
        }

        @Override
        public void delete(final Integer id) {
        }

        @Override
        public Optional<Abiturient> findById(final Integer id) {
            return Optional.of(abiturient);
        }

        @Override
        public List<Abiturient> findAll() {
            return null;
        }

        @Override
        public Abiturient update(final Abiturient entity) throws RecourseNotFoundException {
            return null;
        }
    };

    @Before
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setAmbiguityIgnored(true)
                .setPropertyCondition(Conditions.isNotNull());

        dtoMapper = new EducationDocumentDtoMapper(modelMapper, abiturientRepository);
    }

    @Test
    public void mapToEntity() {
        EducationDocument educationDocument = EducationDocument.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturient(abiturient)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        EducationDocumentCreateRequest educationDocumentDto = EducationDocumentCreateRequest.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturientId(abiturient.getId())
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        Assert.assertEquals(educationDocument, dtoMapper.mapToEntity(educationDocumentDto));
    }

    @Test
    public void mapToEntity1() {
        EducationDocument educationDocument = EducationDocument.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturient(abiturient)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .id(1)
                .build();

        EducationDocumentUpdateRequest educationDocumentDto = EducationDocumentUpdateRequest.builder()
                .documentType("BB")
                .build();

        EducationDocument expected = EducationDocument.builder()
                .documentType("BB")
                .documentUniqueNumber("1")
                .abiturient(abiturient)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .id(1)
                .build();

        Assert.assertEquals(expected, dtoMapper.mapToEntity(educationDocument, educationDocumentDto));
    }


}