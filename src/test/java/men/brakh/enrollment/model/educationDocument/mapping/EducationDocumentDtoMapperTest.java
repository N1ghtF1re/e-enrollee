package men.brakh.enrollment.model.educationDocument.mapping;

import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentUpdateRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public class EducationDocumentDtoMapperTest {

    private EducationDocumentDtoMapper dtoMapper;

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
        public Enrollee update(final Enrollee entity) throws ResourceNotFoundException {
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

        dtoMapper = new EducationDocumentDtoMapper(modelMapper, enrolleeRepository);
    }

    @Test
    public void mapToEntity() {
        EducationDocument educationDocument = EducationDocument.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .enrollee(enrollee)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .build();

        EducationDocumentCreateRequest educationDocumentDto = EducationDocumentCreateRequest.builder()
                .documentType("A")
                .documentUniqueNumber("1")
                .enrolleeId(enrollee.getId())
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
                .enrollee(enrollee)
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
                .enrollee(enrollee)
                .averageGrade(4.3)
                .educationalInstitution("C")
                .id(1)
                .build();

        Assert.assertEquals(expected, dtoMapper.mapToEntity(educationDocument, educationDocumentDto));
    }


}