package men.brakh.enrollment.model.ctCertificate.mapping;

import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.ctCertificate.dto.BaseCtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateUpdateRequest;
import org.modelmapper.ModelMapper;

public class CtCertificateDtoMapper implements CreateDtoMapper<CtCertificateCreateRequest, CtCertificate>,
        UpdateDtoMapper<CtCertificateUpdateRequest, CtCertificate>,
        DtoMapper<CtCertificateDto, CtCertificate> {
    private final ModelMapper modelMapper;
    private final EnrolleeRepository enrolleeRepository;

    public CtCertificateDtoMapper(final ModelMapper modelMapper,
                                  final EnrolleeRepository enrolleeRepository) {
        this.modelMapper = modelMapper;
        this.enrolleeRepository = enrolleeRepository;


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
        ctCertificate.setEnrollee(enrolleeRepository.findById(createRequest.getEnrolleeId()).orElse(null));

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

        Subject subject = subjectName != null ? Subject.fromSubjectName(subjectName) : null;
        if (subject != null)
            ctCertificate.setSubject(subject);

        return ctCertificate;
    }
}
