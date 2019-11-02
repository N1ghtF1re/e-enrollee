package men.brakh.migrator.types;

import org.json.JSONObject;

import java.util.Set;

public class SqlValuesFactory {
  public static SqlValue create(final JSONObject object,
                                final String fieldName,
                                final JSONObject fieldDefinition,
                                final Set<String> requiredFields) {
    final String jsonType = fieldDefinition.getString("type");
    switch (jsonType) {
      case "integer":
        return new IntSqlValue(fieldName, object, requiredFields.contains(fieldName));
      case "string":
        return new VarcharValue(fieldName, object, requiredFields.contains(fieldName));
      case "number":
        return new NumberSqlType(fieldName, object, requiredFields.contains(fieldName));
      case "object":
        return new IntSqlValue(
            object.getJSONObject(
                fieldName).getInt("id"), fieldName + "_id",
                requiredFields.contains(fieldName)
        );
      default:
        throw new RuntimeException("Unknown type " + jsonType);
    }
  }

  public static SqlValue create(final Object object,
                                final String fieldName,
                                final JSONObject fieldDefinition,
                                final Set<String> requiredFields) {
    final String jsonType = fieldDefinition.getString("type");
    switch (jsonType) {
      case "integer":
        return new IntSqlValue((Integer) object, fieldName, requiredFields.contains(fieldName));
      case "string":
        return new VarcharValue((String) object, fieldName, requiredFields.contains(fieldName));
      case "number":
        return new NumberSqlType((Double) object, fieldName, requiredFields.contains(fieldName));
      case "object":
        return new IntSqlValue(
            ((JSONObject) object).getInt("id"),fieldName + "_id",
            requiredFields.contains(fieldName)
        );
      default:
        throw new RuntimeException("Unsupported type " + jsonType);
    }
  }
}
