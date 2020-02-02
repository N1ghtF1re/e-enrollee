package men.brakh.enrollment.application.service;

import men.brakh.enrollment.domain.CreateDto;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.domain.UpdateDto;

public interface CRUDEntityService<
        D extends Dto,
        C extends CreateDto,
        U extends UpdateDto,
        I> extends EntityCreateService<D, C>, EntityUpdateService<D, U, I>,
                   EntityDeleteService<I>, EntityReadService<D, I> {

}
