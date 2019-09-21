package men.brakh.enrollment.model.educationDocument.mapping;

import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EducationDocumentEntityPresenter implements EntityPresenter<EducationDocument, EducationDocumentDto> {
    private final ModelMapper modelMapper;

    public EducationDocumentEntityPresenter(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public EducationDocumentDto mapToDto(final EducationDocument entity, final Class<? extends EducationDocumentDto> dtoClass) {
        EducationDocumentDto documentDto = modelMapper.map(entity, dtoClass);

        if (entity.getEnrollee() != null) {
            documentDto.setEnrolleeId(entity.getEnrollee().getId());
            documentDto.setEnrolleeName(entity.getEnrollee().getFullName());
        }

        return documentDto;
    }

    @Override
    public List<EducationDocumentDto> mapListToDto(final List<EducationDocument> entities, final Class<? extends EducationDocumentDto> dtoClass) {
        return entities
                .stream()
                .map(educationDocument -> mapToDto(educationDocument, dtoClass))
                .collect(Collectors.toList());
    }
}
