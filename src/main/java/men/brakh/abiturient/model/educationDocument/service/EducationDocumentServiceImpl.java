package men.brakh.abiturient.model.educationDocument.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class EducationDocumentServiceImpl extends AbstractCRUDEntityService<
        EducationDocument,
        EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> implements EducationDocumentService {

    private final EducationDocumentRepository educationDocumentRepository;


    public EducationDocumentServiceImpl(final CRUDRepository<EducationDocument, Integer> crudRepository,
                                        final DtoMapper<EducationDocumentDto, EducationDocument> dtoMapper,
                                        final EntityPresenter<EducationDocument, EducationDocumentDto> entityPresenter,
                                        final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        educationDocumentRepository = (EducationDocumentRepository) crudRepository;
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
    public List<EducationDocumentDto> getByAbiturientId(final Integer id) {
       return getTemplate.findBy(
                () -> educationDocumentRepository.findByAbiturientId(id),
                EducationDocumentDto.class
        );
    }
}
