package men.brakh.enrollment.model.ctCertificate.repository;

import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CtCertificateMysqlRepository extends MysqlCRUDRepository<CtCertificate, Integer> implements CtCertificateRepository {
  private final MysqlCRUDRepository<Enrollee, ?> enrolleeRepository;

  public CtCertificateMysqlRepository(final ConnectionPool connectionPool, final String tableName,
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
  public List<CtCertificate> findByEnrolleeId(final Integer enrolleeId) {
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
  public List<CtCertificate> findByYearAndSubject(final Integer enrolleeId, final Integer year, final Subject subject) {
    return find(
        getSelectQuery()
            + " AND " + getTableName() + ".enrollee_id = ?"
            + " AND " + getTableName() + ".year = ? "
            + "AND " + getTableName() + ".subject = ?",
        preparedStatement -> {
          try {
            preparedStatement.setInt(1, enrolleeId);
            preparedStatement.setInt(2, year);
            preparedStatement.setString(3, subject.toString());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  @Override
  public List<CtCertificate> findByCertificateIdentifiers(final String certificateIdentifier, final String certificateNumber) {
    return find(
        getSelectQuery() + " AND " + getTableName() + ".certificateIdentifier = ? AND "
            + getTableName() + ".certificateNumber = ?",
        preparedStatement -> {
          try {
            preparedStatement.setString(1, certificateIdentifier);
            preparedStatement.setString(2, certificateNumber);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "certificateIdentifier",
        "certificateNumber",
        "year",
        "subject",
        "ctPoints",
        "enrollee_id",
        "id"
    );
  }

  @Override
  public CtCertificate extractEntity(final ResultSet resultSet) throws SQLException {

    return CtCertificate.builder()
        .certificateIdentifier(resultSet.getString(getTableName() + ".certificateIdentifier"))
        .certificateNumber(resultSet.getString(getTableName() + ".certificateNumber"))
        .year(resultSet.getInt(getTableName() + ".year"))
        .subject(Subject.valueOf(resultSet.getString(getTableName() + ".subject")))
        .ctPoints(resultSet.getInt(getTableName() + ".ctPoints"))
        .enrollee(enrolleeRepository.extractEntity(resultSet))
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
  protected int initializeModificationQuery(final PreparedStatement preparedStatement, final CtCertificate entity) throws SQLException {
    preparedStatement.setString(1, entity.getCertificateIdentifier());
    preparedStatement.setString(2, entity.getCertificateNumber());
    preparedStatement.setInt(3, entity.getYear());
    preparedStatement.setString(4, entity.getSubject().toString());
    preparedStatement.setInt(5, entity.getCtPoints());
    preparedStatement.setInt(6, entity.getEnrollee().getId());
    return 6;
  }
}
