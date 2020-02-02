package men.brakh.enrollment.domain.educationDocument.service;

import java.util.List;
import javax.validation.Validator;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentDtoMapper;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.domain.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import org.springframework.stereotype.Service;

@Service
public class EducationDocumentServiceImpl extends AbstractCRUDEntityService<
        EducationDocument,
        EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> implements EducationDocumentService {

    private final EducationDocumentRepository educationDocumentRepository;


    public EducationDocumentServiceImpl(final EducationDocumentRepository crudRepository,
                                        final EducationDocumentDtoMapper dtoMapper,
                                        final EducationDocumentEntityPresenter entityPresenter,
                                        final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        educationDocumentRepository = crudRepository;
    }

    @Override
    public EducationDocumentDto create(final EducationDocumentCreateRequest createRequest) throws BadRequestException {
        return createTemplate.save(createRequest, EducationDocumentDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);
    }

    @Override
    public EducationDocumentDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, EducationDocumentDto.class);
    }

    @Override
    public List<EducationDocumentDto> getAll() {
        return getTemplate.getAll(EducationDocumentDto.class);
    }


    @Override
    public EducationDocumentDto update(final Integer id, final EducationDocumentUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, EducationDocumentDto.class);
    }

    @Override
    public List<EducationDocumentDto> getByEnrolleeId(final Integer id) {
       return getTemplate.findBy(
                () -> educationDocumentRepository.findByEnrolleeId(id),
                EducationDocumentDto.class
        );
    }
}
