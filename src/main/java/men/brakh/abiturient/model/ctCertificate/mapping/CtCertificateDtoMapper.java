package men.brakh.abiturient.model.ctCertificate.mapping;

import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.BaseCtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

public class CtCertificateDtoMapper implements CreateDtoMapper<CtCertificateCreateRequest, CtCertificate>,
        UpdateDtoMapper<CtCertificateUpdateRequest, CtCertificate>,
        DtoMapper<CtCertificateDto, CtCertificate> {
    private final ModelMapper modelMapper;
    private final AbiturientRepository abiturientRepository;

    public CtCertificateDtoMapper(final ModelMapper modelMapper,
                                  final AbiturientRepository abiturientRepository) {
        this.modelMapper = modelMapper;
        this.abiturientRepository = abiturientRepository;


        modelMapper.typeMap(CtCertificateCreateRequest.class, CtCertificate.class).addMappings(mp -> {
            mp.skip(CtCertificate::setSubject);
        });

        modelMapper.typeMap(CtCertificateCreateRequest.class, CtCertificate.class).addMappings(mp -> {
            mp.skip(CtCertificate::setId);
        });
        modelMapper.typeMap(CtCertificateUpdateRequest.class, CtCertificate.class).addMappings(mp -> {
            mp.skip(CtCertificate::setSubject);
        });
        modelMapper.typeMap(CtCertificateDto.class, CtCertificate.class).addMappings(mp -> {
            mp.skip(CtCertificate::setSubject);
        });
    }

    @Override
    public CtCertificate mapToEntity(final CtCertificateCreateRequest createRequest) {
        CtCertificate ctCertificate = modelMapper.map(createRequest, CtCertificate.class);
        ctCertificate.setAbiturient(abiturientRepository.findById(createRequest.getAbiturientId()).orElse(null));

        return baseMapping(createRequest, ctCertificate);
    }

    @Override
    public CtCertificate mapToEntity(final CtCertificate entity, final CtCertificateUpdateRequest updateRequest) {
        modelMapper.map(updateRequest, entity);

        return baseMapping(updateRequest, entity);
    }

    @Override
    public CtCertificate mapToEntity(final CtCertificateDto dto) {
        CtCertificate ctCertificate = modelMapper.map(dto, CtCertificate.class);

        return baseMapping(dto, ctCertificate);
    }

    private CtCertificate baseMapping(final BaseCtCertificateDto baseCtCertificateDto,
                                      final CtCertificate ctCertificate) {
        String subjectName = baseCtCertificateDto.getSubject();

        Optional<Subject> foundSubject = Arrays.stream(Subject.values())
                .filter(subject -> subject.getSubjectName().equals(subjectName))
                .findFirst();

        foundSubject.ifPresent(ctCertificate::setSubject);

        return ctCertificate;
    }
}
