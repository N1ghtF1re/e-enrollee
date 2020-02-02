package men.brakh.enrollment.application.template;

import java.util.List;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class SearchTemplate<T extends BaseEntity, D extends Dto> {
  private final JpaSpecificationExecutor<T> specificationExecutor;
  private final EntityPresenter presenter;


  public SearchTemplate(final JpaSpecificationExecutor<T> specificationExecutor, final EntityPresenter presenter) {
    this.specificationExecutor = specificationExecutor;
    this.presenter = presenter;
  }

  @SuppressWarnings("uncheked")
  public List<D> search(final SearchRequest searchRequest, Class<D> dtoClass) throws BadRequestException {
    try {
      final List<T> entities = specificationExecutor.findAll(searchRequest);
      return presenter.mapListToDto(entities, dtoClass);
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
  }
}
