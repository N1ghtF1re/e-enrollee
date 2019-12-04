package men.brakh.enrollment.model.employee.credentials;

import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EmployeeCredentialsMysqlRepository
    extends MysqlCRUDRepository<EmployeeCredentials, Integer> implements EmployeeCredentialsRepository {

  public EmployeeCredentialsMysqlRepository(final ConnectionPool connectionPool, final String tableName) {
    super(connectionPool, tableName);
  }

  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "employee_id",
        "password",
        "salt",
        "id"
    );
  }

  @Override
  public EmployeeCredentials extractEntity(final ResultSet resultSet) throws SQLException {
    return EmployeeCredentials.builder()
        .id(resultSet.getInt(getTableName() + ".id"))
        .employeeId(resultSet.getInt(getTableName() + ".employee_id"))
        .salt(resultSet.getBytes(getTableName() + ".salt"))
        .password(resultSet.getBytes(getTableName() + ".password"))
        .build();
  }

  @Override
  protected void setId(final PreparedStatement preparedStatement, final Integer id, final int index) throws SQLException {
    preparedStatement.setInt(index, id);
  }

  @Override
  protected Integer getId(final ResultSet generatedKeys) throws SQLException {
    return generatedKeys.getInt(1);
  }

  @Override
  protected int initializeModificationQuery(final PreparedStatement preparedStatement, final EmployeeCredentials entity) throws SQLException {
    preparedStatement.setInt(1, entity.getEmployeeId());
    preparedStatement.setBytes(2, entity.getPassword());
    preparedStatement.setBytes(3, entity.getSalt());
    return 3;
  }

  @Override
  public List<EmployeeCredentials> findByEmployeeId(final Integer employeeId) {
    return find(
        getSelectQuery() + " WHERE " + getTableName() + ".employee_id = ?",
        preparedStatement -> {
          try {
            preparedStatement.setInt(1, employeeId);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }
}
