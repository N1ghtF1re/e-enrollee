package men.brakh.enrollment.application.search;

import lombok.Data;

@Data
public class Sort {
  private String field;
  private SortType type;

  public enum SortType {
    ASC,
    DESC
  }
}
