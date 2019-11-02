package men.brakh.migrator.types;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VarcharValue extends SqlValue<String> {


  public VarcharValue(final String fieldName,
                      final JSONObject object,
                      final boolean required) {
    super(fieldName, object, required);
  }

  public VarcharValue(final String value, final String fieldName, final boolean required) {
    super(value, fieldName, required);
  }

  @Override
  public String getSqlTypeName() {
    return "VARCHAR(255) " + (required ? "NOT NULL" : "DEFAULT NULL");
  }

  @Override
  protected String getValueFromJson(final JSONObject jsonObject, final String fieldName) {
    return jsonObject.getString(fieldName);
  }

  @Override
  public void setValue(final PreparedStatement statement, final int index) throws SQLException {
    statement.setString(index, value);
  }
}
