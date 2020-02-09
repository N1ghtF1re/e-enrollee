package men.brakh.enrollment.application.template;

import java.util.List;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.search.SearchResponse;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class SearchTemplate<T extends BaseEntity, D extends Dto> {
  private final JpaSpecificationExecutor<T> specificationExecutor;
  private final EntityPresenter presenter;


  public SearchTemplate(final JpaSpecificationExecutor<T> specificationExecutor, final EntityPresenter presenter) {
    this.specificationExecutor = specificationExecutor;
    this.presenter = presenter;
  }

  @SuppressWarnings("uncheked")
  public SearchResponse<D> search(final SearchRequest searchRequest, Class<D> dtoClass) throws BadRequestException {
    try {

      final Page<T> page = specificationExecutor.findAll(searchRequest,searchRequest.getPageable());
      final List<D> content = presenter.mapListToDto(page.getContent(), dtoClass);

      return SearchResponse.<D>builder()
          .content(content)
          .page(searchRequest.getPage())
          .pageSize(searchRequest.getPageSize())
          .totalPages(page.getTotalPages())
          .build();
    } catch (Exception e) {
      throw new BadRequestException(e);
    }
  }
}
