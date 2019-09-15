package men.brakh.abiturient.model.ctCertificate.mapping;

import men.brakh.abiturient.exception.RecourseNotFoundException;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CtCertificateDtoMapperTest {

    private CtCertificateDtoMapper dtoMapper;

    private Abiturient abiturient = Abiturient.builder()
            .id(1)
            .firstName("A")
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

        dtoMapper = new CtCertificateDtoMapper(modelMapper, abiturientRepository);
    }

    @Test
    public void mapToEntity() {
        CtCertificateCreateRequest ctCertificateCreateRequest = CtCertificateCreateRequest.builder()
                .certificateId("111-11-1")
                .certificateNumber("1111111")
                .ctPoints(1)
                .abiturientId(1)
                .subject("History")
                .build();

        CtCertificate ctCertificate = CtCertificate.builder()
                .subject(Subject.HISTORY)
                .ctPoints(1)
                .certificateNumber("1111111")
                .certificateIdentifier("111-11-1")
                .abiturient(abiturient)
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
                .subject("History")
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