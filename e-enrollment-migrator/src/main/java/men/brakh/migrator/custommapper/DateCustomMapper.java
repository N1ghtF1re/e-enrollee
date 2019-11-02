package men.brakh.migrator.custommapper;

import men.brakh.migrator.types.DateSqlValue;
import men.brakh.migrator.types.SqlValue;

import java.util.Date;

public class DateCustomMapper extends CustomMapper {
  public DateCustomMapper(final String fieldName, final String tableName) {
    super(fieldName, tableName);
  }

  @Override
  public SqlValue map(final SqlValue oldValue) {
    final Date date = new Date((Integer) oldValue.getValue() * 1000L);
    return new DateSqlValue(
        date,
        oldValue.getFieldName(),
        oldValue.isRequired()
    );
  }
}
