package men.brakh.enrollment.model.educationDocument.mapping;

import men.brakh.enrollment.exception.RecourseNotFoundException;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class EducationDocumentEntityPresenterTest {

    private EducationDocumentEntityPresenter entityPresenter;

    private Enrollee enrollee = Enrollee.builder()
            .id(1)
            .firstName("A")
            .lastName("B")
            .middleName("C")
            .build();

    private EnrolleeRepository enrolleeRepository = new EnrolleeRepository() {
        @Override
        public Enrollee create(final Enrollee entity) {
            return null;
        }

        @Override
        public void delete(final Integer id) {
        }

        @Override
        public Optional<Enrollee> findById(final Integer id) {
            return Optional.of(enrollee);
        }

        @Override
        public List<Enrollee> findAll() {
            return null;
        }

        @Override
        public Enrollee update(final Enrollee entity) throws RecourseNotFoundException {
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
                .enrollee(enrollee)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        EducationDocumentDto educationDocumentDto = EducationDocumentDto.builder()
                .id(1)
                .documentType("A")
                .documentUniqueNumber("1")
                .enrolleeId(enrollee.getId())
                .enrolleeName("A C B")
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        assertEquals(educationDocumentDto, entityPresenter.mapToDto(educationDocument, EducationDocumentDto.class));
    }
}