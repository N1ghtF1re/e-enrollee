package men.brakh.enrollment.service;

import men.brakh.enrollment.model.CreateDto;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.UpdateDto;

public interface CRUDEntityService<
        D extends Dto,
        C extends CreateDto,
        U extends UpdateDto,
        I> extends EntityCreateService<D, C>, EntityUpdateService<D, U, I>,
                   EntityDeleteService<I>, EntityReadService<D, I> {

}
