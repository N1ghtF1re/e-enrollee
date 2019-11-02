package men.brakh.migrator.types;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqlValue<T> {
  protected T value;
  protected String fieldName;
  protected boolean required;

  public SqlValue(final T value,
                  final String fieldName,
                  final boolean required) {
    this.value = value;
    this.fieldName = fieldName;
    this.required = required;
  }

  public SqlValue(final String fieldName,
                  final JSONObject object,
                  final boolean required) {
    final T value;

    if(object.has(fieldName) && !object.isNull(fieldName)) {
      value =  getValueFromJson(object, fieldName);
    } else {
      value = null;
    }

    this.value = value;
    this.fieldName = fieldName;
    this.required = required;
  }

  public T getValue() {
    return value;
  }
  public String getFieldName() {
    return fieldName;
  }

  public boolean isRequired() {
    return required;
  }

  public abstract String getSqlTypeName();
  protected abstract T getValueFromJson(final JSONObject jsonObject, final String fieldName);
  public abstract void setValue(final PreparedStatement statement, final int index) throws SQLException;
}
