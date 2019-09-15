package men.brakh.abiturient.service;

import men.brakh.abiturient.model.CreateDto;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.model.UpdateDto;

public interface CRUDEntityService<
        D extends Dto,
        C extends CreateDto,
        U extends UpdateDto,
        I> extends EntityCreateService<D, C>, EntityUpdateService<D, U, I>,
                   EntityDeleteService<I>, EntityReadService<D, I> {

}
