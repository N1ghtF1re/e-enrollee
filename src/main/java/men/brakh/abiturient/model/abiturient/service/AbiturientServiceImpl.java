package men.brakh.abiturient.model.abiturient.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.abiturient.dto.AbiturientCreateRequest;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.dto.AbiturientUpdateRequest;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class AbiturientServiceImpl extends AbstractCRUDEntityService<
        Abiturient,
        AbiturientDto,
        AbiturientCreateRequest,
        AbiturientUpdateRequest,
        Integer
        > implements AbiturientService {

    public AbiturientServiceImpl(final CRUDRepository<Abiturient, Integer> crudRepository,
                                 final DtoMapper<AbiturientDto, Abiturient> dtoMapper,
                                 final EntityPresenter<Abiturient, AbiturientDto> entityPresenter,
                                 final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
    }

    @Override
    public AbiturientDto create(final AbiturientCreateRequest createRequest) throws BadRequestException {
        return createTemplate.save(createRequest, AbiturientDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);
    }

    @Override
    public AbiturientDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, AbiturientDto.class);
    }

    @Override
    public List<AbiturientDto> getAll() {
        return getTemplate.getAll(AbiturientDto.class);
    }

    @Override
    public AbiturientDto update(final Integer id, final AbiturientUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, AbiturientDto.class);
    }
}
