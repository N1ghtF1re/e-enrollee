package men.brakh.enrollment.model.educationDocument.repository;

import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EducationDocumentMysqlRepository extends MysqlCRUDRepository<EducationDocument, Integer>
    implements EducationDocumentRepository {
  private final MysqlCRUDRepository<Enrollee, ?> enrolleeRepository;

  public EducationDocumentMysqlRepository(final ConnectionPool connectionPool, final String tableName,
                                          final MysqlCRUDRepository<Enrollee, ?> enrolleeRepository) {
    super(connectionPool, tableName);
    this.enrolleeRepository = enrolleeRepository;
  }

  @Override
  protected String getSelectQuery() {
    // join enrollee
    return "SELECT " + getSqlFieldsAsString() + ", " + enrolleeRepository.getSqlFieldsAsString() + " FROM " + getTableName()
        + " INNER JOIN " + enrolleeRepository.getTableName()
        + " ON " + getTableName() + ".enrollee_id = " + enrolleeRepository.getTableName() + ".id";
  }

  @Override
  public List<EducationDocument> findByEnrolleeId(final Integer enrolleeId) {
    return find(
        getSelectQuery() + " AND " + getTableName() + ".enrollee_id = ?",
        preparedStatement -> {
          try {
            preparedStatement.setInt(1, enrolleeId);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "averageGrade",
        "documentType",
        "documentUniqueNumber",
        "enrollee_id",
        "educationalInstitution",
        "id"
    );
  }

  @Override
  public EducationDocument extractEntity(final ResultSet resultSet) throws SQLException {
    return EducationDocument.builder()
        .averageGrade(resultSet.getDouble(getTableName() + ".averageGrade"))
        .documentType(resultSet.getString(getTableName() + ".documentType"))
        .documentUniqueNumber(resultSet.getString(getTableName() + ".documentUniqueNumber"))
        .enrollee(enrolleeRepository.extractEntity(resultSet))
        .educationalInstitution(resultSet.getString(getTableName() + ".educationalInstitution"))
        .id(resultSet.getInt(getTableName() + ".id"))
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
  protected int initializeModificationQuery(final PreparedStatement preparedStatement, final EducationDocument entity) throws SQLException {
    preparedStatement.setDouble(1, entity.getAverageGrade());
    preparedStatement.setString(2, entity.getDocumentType());
    preparedStatement.setString(3, entity.getDocumentUniqueNumber());
    preparedStatement.setInt(4, entity.getEnrollee().getId());
    preparedStatement.setString(5, entity.getEducationalInstitution());
    return 5;
  }
}
