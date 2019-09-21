package men.brakh.abiturient.model.statement.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.model.statement.dto.StatementCreateRequest;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import men.brakh.abiturient.model.statement.dto.StatementUpdateRequest;
import men.brakh.abiturient.model.statement.repository.StatementRepository;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class StatementServiceImpl extends AbstractCRUDEntityService<
        Statement,
        StatementDto,
        StatementCreateRequest,
        StatementUpdateRequest,
        Integer
        > implements StatementService {

    private final StatementRepository statementRepository;

    public StatementServiceImpl(final CRUDRepository<Statement, Integer> crudRepository,
                                final DtoMapper<StatementDto, Statement> dtoMapper,
                                final EntityPresenter<Statement, StatementDto> entityPresenter,
                                final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        statementRepository = (StatementRepository) crudRepository;
    }

    private boolean isAbutiruentAlreadyHasStatement(final Integer abutirueintId) {
        return statementRepository.findByAbiturientId(abutirueintId).size() > 0;
    }

    @Override
    public StatementDto create(final StatementCreateRequest createRequest) throws BadRequestException {
        if (isAbutiruentAlreadyHasStatement(createRequest.getAbiturientId())) {
            throw new BadRequestException("Abiturient can't have more than one statement. "
                    + "Remove old statement to add new");
        }

        return createTemplate.save(createRequest, StatementDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);
    }

    @Override
    public StatementDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, StatementDto.class);
    }

    @Override
    public List<StatementDto> getAll() {
        return getTemplate.getAll(StatementDto.class);
    }

    @Override
    public StatementDto update(final Integer id,
                               final StatementUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, StatementDto.class);
    }
}
