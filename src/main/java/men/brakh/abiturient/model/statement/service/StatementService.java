package men.brakh.abiturient.model.statement.service;

import men.brakh.abiturient.model.statement.dto.StatementCreateRequest;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import men.brakh.abiturient.model.statement.dto.StatementUpdateRequest;
import men.brakh.abiturient.service.CRUDEntityService;

public interface StatementService extends CRUDEntityService<
        StatementDto,
        StatementCreateRequest,
        StatementUpdateRequest,
        Integer
        > {
}
