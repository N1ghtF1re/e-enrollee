package men.brakh.migrator.types;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntSqlValue extends SqlValue<Integer> {

  public IntSqlValue(final String fieldName,
                     final JSONObject object,
                     final boolean required) {
    super(fieldName, object, required);
  }

  public IntSqlValue(final Integer value, final String fieldName, final boolean required) {
    super(value, fieldName, required);
  }

  @Override
  public String getSqlTypeName() {
    if (fieldName.equals("id")) {
      return "INT PRIMARY KEY AUTO_INCREMENT";
    }

    return "INT " + (required ? "NOT NULL" : "DEFAULT NULL");
  }

  @Override
  protected Integer getValueFromJson(final JSONObject jsonObject, final String fieldName) {
    return jsonObject.getInt(fieldName);
  }

  @Override
  public void setValue(final PreparedStatement statement, final int index) throws SQLException {
    statement.setInt(index, value);
  }
}
