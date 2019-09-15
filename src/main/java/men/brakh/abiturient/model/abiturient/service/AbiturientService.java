package men.brakh.abiturient.model.abiturient.service;

import men.brakh.abiturient.model.abiturient.dto.AbiturientCreateRequest;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.dto.AbiturientUpdateRequest;
import men.brakh.abiturient.service.CRUDEntityService;

public interface AbiturientService extends CRUDEntityService<
        AbiturientDto,
        AbiturientCreateRequest,
        AbiturientUpdateRequest,
        Integer> {
}
