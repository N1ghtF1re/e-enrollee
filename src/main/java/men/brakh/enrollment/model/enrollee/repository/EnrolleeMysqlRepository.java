package men.brakh.enrollment.model.enrollee.repository;

import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EnrolleeMysqlRepository extends MysqlCRUDRepository<Enrollee, Integer> implements EnrolleeRepository {

  public EnrolleeMysqlRepository(final ConnectionPool connectionPool, final String tableName) {
    super(connectionPool, tableName);
  }

  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "firstName",
        "lastName",
        "middleName",
        "id",
        "birthDate"
    );
  }

  @Override
  public Enrollee extractEntity(final ResultSet resultSet) throws SQLException {
    return Enrollee.builder()
        .id(resultSet.getInt(getTableName() + ".id"))
        .birthDate(resultSet.getDate(getTableName() + ".birthDate"))
        .firstName(resultSet.getString(getTableName() + ".firstName"))
        .lastName(resultSet.getString(getTableName() + ".lastName"))
        .middleName(resultSet.getString(getTableName() + ".middleName"))
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
  protected int initializeModificationQuery(final PreparedStatement preparedStatement, final Enrollee entity) throws SQLException {
    preparedStatement.setString(1, entity.getFirstName());
    preparedStatement.setString(2, entity.getLastName());
    preparedStatement.setString(3, entity.getMiddleName());
    preparedStatement.setDate(4, new java.sql.Date(entity.getBirthDate().getTime()));

    return 4;
  }
}
