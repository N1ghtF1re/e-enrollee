package men.brakh.enrollment.domain.enrollee.service;

import java.util.List;
import javax.validation.Validator;
import men.brakh.enrollment.application.search.SearchResponse;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.domain.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import men.brakh.enrollment.application.template.SearchTemplate;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/api/v1/enrollees")
public class EnrolleeServiceImpl extends AbstractCRUDEntityService<
        Enrollee,
        EnrolleeDto,
        EnrolleeCreateRequest,
        EnrolleeUpdateRequest,
        Integer
        > implements EnrolleeService {

    private final SearchTemplate<Enrollee, EnrolleeDto> searchTemplate;

    public EnrolleeServiceImpl(final EnrolleeRepository crudRepository,
                               final DtoMapper<EnrolleeDto, Enrollee> dtoMapper,
                               final EntityPresenter<Enrollee, EnrolleeDto> entityPresenter,
                               final Validator validator
    ) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        searchTemplate = new SearchTemplate<>(crudRepository, entityPresenter);
    }

    @Override
    @PostMapping
    public @ResponseBody EnrolleeDto create(
        @RequestBody final EnrolleeCreateRequest createRequest
    ) throws BadRequestException {
        return createTemplate.save(createRequest, EnrolleeDto.class);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Integer id) throws BadRequestException, ResourceNotFoundException {
        deleteTemplate.delete(id);

    }

    @Override
    @GetMapping("/{id}")
    public @ResponseBody EnrolleeDto getById(@PathVariable("id") final Integer id) throws ResourceNotFoundException {
        return getTemplate.getById(id, EnrolleeDto.class);
    }

    @Override
    @GetMapping
    public @ResponseBody List<EnrolleeDto> getAll() {
        return getTemplate.getAll(EnrolleeDto.class);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody EnrolleeDto update(@PathVariable("id") final Integer id,
                                            @RequestBody final EnrolleeUpdateRequest updateRequest
    ) throws BadRequestException, ResourceNotFoundException {
        return updateTemplate.update(id, updateRequest, EnrolleeDto.class);
    }

    @PostMapping("/search")
    public @ResponseBody
    SearchResponse<EnrolleeDto> search(@RequestBody final SearchRequest searchRequest) throws BadRequestException {
        return searchTemplate.search(searchRequest, EnrolleeDto.class);
    }
}
