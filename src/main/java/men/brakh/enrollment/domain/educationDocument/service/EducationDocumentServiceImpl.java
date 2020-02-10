package men.brakh.enrollment.domain.educationDocument.service;

import java.util.List;
import javax.validation.Validator;
import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.search.SearchResponse;
import men.brakh.enrollment.application.template.SearchTemplate;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentDtoMapper;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.domain.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequestMapping(path = "/api/v1/education-documents")
public class EducationDocumentServiceImpl extends AbstractCRUDEntityService<
        EducationDocument,
        EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> implements EducationDocumentService {

    private SearchTemplate<EducationDocument, EducationDocumentDto> searchTemplate;

    public EducationDocumentServiceImpl(final EducationDocumentRepository crudRepository,
                                        final EducationDocumentDtoMapper dtoMapper,
                                        final EducationDocumentEntityPresenter entityPresenter,
                                        final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        this.searchTemplate = new SearchTemplate<>(
            crudRepository,
            entityPresenter
        );
    }

    @Override
    @PostMapping
    public @ResponseBody EducationDocumentDto create(
        final @RequestBody EducationDocumentCreateRequest createRequest
    ) throws BadRequestException {
        return createTemplate.save(createRequest, EducationDocumentDto.class);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(
        @PathVariable(name = "id") final Integer id
    ) throws BadRequestException, ResourceNotFoundException {
        deleteTemplate.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public @ResponseBody EducationDocumentDto getById(
        @PathVariable(name = "id") final Integer id
    ) throws ResourceNotFoundException {
        return getTemplate.getById(id, EducationDocumentDto.class);
    }

    @Override
    public List<EducationDocumentDto> getAll() {
        return getTemplate.getAll(EducationDocumentDto.class);
    }


    @Override
    @PutMapping("/{id}")
    public @ResponseBody EducationDocumentDto update(
        @PathVariable(name = "id") final Integer id,
        @RequestBody final EducationDocumentUpdateRequest updateRequest
    ) throws BadRequestException, ResourceNotFoundException {
        return updateTemplate.update(id, updateRequest, EducationDocumentDto.class);
    }

    @Override
    @PostMapping("/search")
    public @ResponseBody SearchResponse<EducationDocumentDto> search(
        final @RequestBody SearchRequest searchRequest
    ) throws BadRequestException {
        return searchTemplate.search(searchRequest, EducationDocumentDto.class);
    }
}
