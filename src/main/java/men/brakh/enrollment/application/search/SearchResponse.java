package men.brakh.enrollment.application.search;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import men.brakh.enrollment.domain.Dto;

@Data
@Builder
public class SearchResponse<D extends Dto> {
  private int totalPages;
  private int page;
  private int pageSize;
  private List<D> content;
}
