package men.brakh.migrator.custommapper;

import men.brakh.migrator.types.SqlValue;

public abstract class CustomMapper {
  protected String fieldName;
  protected String tableName;

  public CustomMapper(final String fieldName,
                      final String tableName) {
    this.fieldName = fieldName;
    this.tableName = tableName;
  }

  public boolean isNeedToCustomMap(final String fieldName,
                                   final String tableName) {
    return this.fieldName.equals(fieldName) && this.tableName.equals(tableName);
  }

  public abstract SqlValue map(final SqlValue oldValue);
}
