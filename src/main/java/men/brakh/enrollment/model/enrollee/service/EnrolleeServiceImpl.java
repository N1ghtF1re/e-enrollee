package men.brakh.enrollment.model.enrollee.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.repository.CRUDRepository;
import men.brakh.enrollment.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class EnrolleeServiceImpl extends AbstractCRUDEntityService<
        Enrollee,
        EnrolleeDto,
        EnrolleeCreateRequest,
        EnrolleeUpdateRequest,
        Integer
        > implements EnrolleeService {


    public EnrolleeServiceImpl(final CRUDRepository<Enrollee, Integer> crudRepository,
                                 final DtoMapper<EnrolleeDto, Enrollee> dtoMapper,
                                 final EntityPresenter<Enrollee, EnrolleeDto> entityPresenter,
                                 final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
    }

    @Override
    public EnrolleeDto create(final EnrolleeCreateRequest createRequest) throws BadRequestException {
        return createTemplate.save(createRequest, EnrolleeDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);

    }

    @Override
    public EnrolleeDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, EnrolleeDto.class);
    }

    @Override
    public List<EnrolleeDto> getAll() {
        return getTemplate.getAll(EnrolleeDto.class);
    }

    @Override
    public EnrolleeDto update(final Integer id, final EnrolleeUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, EnrolleeDto.class);
    }
}
