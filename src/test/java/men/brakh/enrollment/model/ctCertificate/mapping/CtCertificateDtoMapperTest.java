package men.brakh.enrollment.model.ctCertificate.mapping;

import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CtCertificateDtoMapperTest {

    private CtCertificateDtoMapper dtoMapper;

    private Enrollee enrollee = Enrollee.builder()
            .id(1)
            .firstName("A")
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

        dtoMapper = new CtCertificateDtoMapper(modelMapper, enrolleeRepository);
    }

    @Test
    public void mapToEntity() {
        CtCertificateCreateRequest ctCertificateCreateRequest = CtCertificateCreateRequest.builder()
                .certificateId("111-11-1")
                .certificateNumber("1111111")
                .ctPoints(1)
                .enrolleeId(1)
                .subject("HISTORY")
                .build();

        CtCertificate ctCertificate = CtCertificate.builder()
                .subject(Subject.HISTORY)
                .ctPoints(1)
                .certificateNumber("1111111")
                .certificateIdentifier("111-11-1")
                .enrollee(enrollee)
                .build();

        assertEquals(ctCertificate, dtoMapper.mapToEntity(ctCertificateCreateRequest));
    }

    @Test
    public void mapToEntity1() {
        CtCertificate ctCertificate = CtCertificate.builder()
                .id(1)
                .subject(Subject.HISTORY)
                .ctPoints(1)
                .certificateNumber("1111111")
                .certificateIdentifier("111-11-1")
                .build();

        CtCertificateUpdateRequest updateRequest = CtCertificateUpdateRequest.builder()
                .ctPoints(3)
                .build();

        CtCertificate expected = CtCertificate.builder()
                .id(1)
                .subject(Subject.HISTORY)
                .ctPoints(3)
                .certificateNumber("1111111")
                .certificateIdentifier("111-11-1")
                .build();

        assertEquals(expected, dtoMapper.mapToEntity(ctCertificate, updateRequest));
    }

    @Test
    public void mapToEntity2() {
        CtCertificateDto ctCertificateDto = CtCertificateDto.builder()
                .certificateId("111-11-1")
                .certificateNumber("1111111")
                .ctPoints(1)
                .subject("HISTORY")
                .build();

        CtCertificate ctCertificate = CtCertificate.builder()
                .subject(Subject.HISTORY)
                .ctPoints(1)
                .certificateNumber("1111111")
                .certificateIdentifier("111-11-1")
                .build();

        assertEquals(ctCertificate, dtoMapper.mapToEntity(ctCertificateDto));
    }
}