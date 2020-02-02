package men.brakh.enrollment.domain.educationDocument.mapping;

import men.brakh.enrollment.application.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.domain.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.educationDocument.dto.BaseEducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EducationDocumentDtoMapper implements DtoMapper<EducationDocumentDto, EducationDocument>,
        CreateDtoMapper<EducationDocumentCreateRequest, EducationDocument>,
        UpdateDtoMapper<EducationDocumentUpdateRequest, EducationDocument> {

    private final ModelMapper modelMapper;
    private final EnrolleeRepository enrolleeRepository;

    public EducationDocumentDtoMapper(final ModelMapper modelMapper,
                                      final EnrolleeRepository enrolleeRepository) {
        this.modelMapper = modelMapper;
        this.enrolleeRepository = enrolleeRepository;

        modelMapper.typeMap(EducationDocumentCreateRequest.class, EducationDocument.class).addMappings(mp -> {
            mp.skip(EducationDocument::setId);
        });
    }


    @Override
    public EducationDocument mapToEntity(final EducationDocumentCreateRequest createRequest) {
        EducationDocument educationDocument = modelMapper.map(createRequest, EducationDocument.class);
        educationDocument.setEnrollee(enrolleeRepository.findById(createRequest.getEnrolleeId()).orElse(null));
        return baseMapping(educationDocument, createRequest);
    }

    @Override
    public EducationDocument mapToEntity(final EducationDocumentDto dto) {
        EducationDocument educationDocument = modelMapper.map(dto, EducationDocument.class);
        return baseMapping(educationDocument, dto);
    }

    @Override
    public EducationDocument mapToEntity(final EducationDocument entity, final EducationDocumentUpdateRequest updateRequest) {
        modelMapper.map(updateRequest, entity);
        return baseMapping(entity, updateRequest);
    }

    private EducationDocument baseMapping(final EducationDocument entity, final BaseEducationDocumentDto documentDto) {
        return entity;
    }
}
