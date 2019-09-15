package men.brakh.abiturient.model.educationDocument.mapping;

import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.BaseEducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentUpdateRequest;
import org.modelmapper.ModelMapper;

public class EducationDocumentDtoMapper implements DtoMapper<EducationDocumentDto, EducationDocument>,
        CreateDtoMapper<EducationDocumentCreateRequest, EducationDocument>,
        UpdateDtoMapper<EducationDocumentUpdateRequest, EducationDocument> {

    private final ModelMapper modelMapper;
    private final AbiturientRepository abiturientRepository;

    public EducationDocumentDtoMapper(final ModelMapper modelMapper,
                                      final AbiturientRepository abiturientRepository) {
        this.modelMapper = modelMapper;
        this.abiturientRepository = abiturientRepository;

        modelMapper.typeMap(EducationDocumentCreateRequest.class, EducationDocument.class).addMappings(mp -> {
            mp.skip(EducationDocument::setId);
        });
    }


    @Override
    public EducationDocument mapToEntity(final EducationDocumentCreateRequest createRequest) {
        EducationDocument educationDocument = modelMapper.map(createRequest, EducationDocument.class);
        educationDocument.setAbiturient(abiturientRepository.findById(createRequest.getAbiturientId()).orElse(null));
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
