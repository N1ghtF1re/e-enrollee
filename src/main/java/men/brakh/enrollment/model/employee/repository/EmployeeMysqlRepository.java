package men.brakh.enrollment.model.employee.repository;

import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.employee.Employee;
import men.brakh.enrollment.model.employee.Role;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EmployeeMysqlRepository extends MysqlCRUDRepository<Employee, Integer> implements EmployeeRepository {

  public EmployeeMysqlRepository(final ConnectionPool connectionPool, final String tableName) {
    super(connectionPool, tableName);
  }

  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "firstName",
        "lastName",
        "middleName",
        "id",
        "role",
        "username"
    );
  }

  @Override
  public Employee extractEntity(final ResultSet resultSet) throws SQLException {
    return Employee.builder()
        .firstName(resultSet.getString(getTableName() + ".firstName"))
        .lastName(resultSet.getString(getTableName() + ".lastName"))
        .middleName(resultSet.getString(getTableName() + ".middleName"))
        .role(Role.valueOf(resultSet.getString(getTableName() + ".role")))
        .id(resultSet.getInt(getTableName() + ".id"))
        .username(resultSet.getString(getTableName() + ".username"))

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
  protected int initializeModificationQuery(final PreparedStatement preparedStatement, final Employee entity) throws SQLException {
    preparedStatement.setString(1, entity.getFirstName());
    preparedStatement.setString(2, entity.getLastName());
    preparedStatement.setString(3, entity.getMiddleName());
    preparedStatement.setString(4, entity.getRole().toString());
    preparedStatement.setString(5, entity.getUsername());
    return 5;
  }

  @Override
  public List<Employee> findByUsername(final String username) {
    return find(
        getSelectQuery() + " WHERE " + getTableName() + ".username = ?",
        preparedStatement -> {
          try {
            preparedStatement.setString(1, username);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }
}
