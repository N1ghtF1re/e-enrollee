package men.brakh.migrator.types;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NumberSqlType extends SqlValue<Double> {
  public NumberSqlType(final Double value, final String fieldName, final boolean required) {
    super(value, fieldName, required);
  }

  public NumberSqlType(final String fieldName, final JSONObject object, final boolean required) {
    super(fieldName, object, required);
  }

  @Override
  public String getSqlTypeName() {
    return "DOUBLE " + (required ? "NOT NULL" : "DEFAULT NULL");
  }

  @Override
  protected Double getValueFromJson(final JSONObject jsonObject, final String fieldName) {
    return jsonObject.getDouble(fieldName);
  }

  @Override
  public void setValue(final PreparedStatement statement, final int index) throws SQLException {
    statement.setDouble(index, value);
  }
}
