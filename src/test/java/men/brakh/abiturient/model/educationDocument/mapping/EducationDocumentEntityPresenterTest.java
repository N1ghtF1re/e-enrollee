package men.brakh.abiturient.model.educationDocument.mapping;

import men.brakh.abiturient.exception.RecourseNotFoundException;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class EducationDocumentEntityPresenterTest {

    private EducationDocumentEntityPresenter entityPresenter;

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

        entityPresenter = new EducationDocumentEntityPresenter(modelMapper);
    }


    @Test
    public void mapToDto() {
        EducationDocument educationDocument = EducationDocument.builder()
                .id(1)
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturient(abiturient)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        EducationDocumentDto educationDocumentDto = EducationDocumentDto.builder()
                .id(1)
                .documentType("A")
                .documentUniqueNumber("1")
                .abiturientId(abiturient.getId())
                .abiturientName("A C B")
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        assertEquals(educationDocumentDto, entityPresenter.mapToDto(educationDocument, EducationDocumentDto.class));
    }
}