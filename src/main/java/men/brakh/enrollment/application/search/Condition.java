package men.brakh.enrollment.application.search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Condition {
  final static transient DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  private Type type;

  private Comparison comparison;

  private Object value;

  private String field;

  public Object getValue() {
    try {
      switch (type) {
        case numeric:
        case string:
        case bool:
        case uuid:
          return value;
        case date:
          if (value instanceof String) {
            return dateFormat.parse(value.toString());
          } else {
            return value;
          }
        case list:
          break;
        case raw:
          break;
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid filter", e);
    }
    throw new IllegalArgumentException();
  }

}