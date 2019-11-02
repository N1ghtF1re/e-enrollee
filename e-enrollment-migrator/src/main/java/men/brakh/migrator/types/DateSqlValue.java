package men.brakh.migrator.types;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class DateSqlValue extends SqlValue<Date> {
  public DateSqlValue(final Date value, final String fieldName, final boolean required) {
    super(value, fieldName, required);
  }

  public DateSqlValue(final String fieldName, final JSONObject object, final boolean required) {
    super(fieldName, object, required);
  }

  @Override
  public String getSqlTypeName() {
    return "TIMESTAMP " + (required ? "NOT NULL" : "DEFAULT NULL");
  }

  @Override
  protected Date getValueFromJson(final JSONObject jsonObject, final String fieldName) {
    return null;
  }

  @Override
  public void setValue(final PreparedStatement statement, final int index) throws SQLException {
    statement.setDate(index, new java.sql.Date(value.getTime()));
  }
}
