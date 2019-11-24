package men.brakh.enrollment.model.universityApplication.repository;

import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.EducationType;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniversityApplicationMysqlRepository extends MysqlCRUDRepository<UniversityApplication, Integer>
    implements UniversityApplicationRepository {

  private final MysqlCRUDRepository<Enrollee, ?> enrolleeRepository;
  private final MysqlCRUDRepository<EducationDocument, ?>educationDocumentRepository;
  private final MysqlCRUDRepository<CtCertificate, Integer> ctCertificateRepository;

  public UniversityApplicationMysqlRepository(final ConnectionPool connectionPool, final String tableName,
                                              final MysqlCRUDRepository<Enrollee, ?> enrolleeRepository,
                                              final MysqlCRUDRepository<EducationDocument, ?> educationDocumentRepository,
                                              final MysqlCRUDRepository<CtCertificate, Integer> ctCertificateRepository) {
    super(connectionPool, tableName);
    this.enrolleeRepository = enrolleeRepository;
    this.educationDocumentRepository = educationDocumentRepository;
    this.ctCertificateRepository = ctCertificateRepository;
  }

  @Override
  public List<UniversityApplication> findByEnrolleeId(final Integer enrolleeId) {
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
  public List<UniversityApplication> findByEnrolleeIdAndType(final Integer enrolleeId, final EducationType type) {
    return find(
        getSelectQuery() + " AND " + getTableName() + ".enrollee_id = ? AND " + getTableName() + ".type = ?",
        preparedStatement -> {
          try {
            preparedStatement.setInt(1, enrolleeId);
            preparedStatement.setString(2, type.toString());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  @Override
  protected String getSelectQuery() {
    // join enrollee
    return "SELECT " + getSqlFieldsAsString()
          + ", " + enrolleeRepository.getSqlFieldsAsString()
          + ", " + educationDocumentRepository.getSqlFieldsAsString()
        + " FROM " + getTableName()
        + " INNER JOIN " + enrolleeRepository.getTableName()
        + " ON " + getTableName() + ".enrollee_id = " + enrolleeRepository.getTableName() + ".id"
        + " INNER JOIN " + educationDocumentRepository.getTableName()
        + " ON " + getTableName() + ".educationDocument_id = " + educationDocumentRepository.getTableName() + ".id";
  }


  @Override
  public List<String> getSqlFields() {
    return Arrays.asList(
        "date",
        "educationDocument_id",
        "type",
        "enrollee_id",
        "id"
    );
  }

  @Override
  public UniversityApplication extractEntity(final ResultSet resultSet) throws SQLException {
    return UniversityApplication.builder()
        .date(resultSet.getDate(getTableName() + ".date"))
        .educationDocument(educationDocumentRepository.extractEntity(resultSet))
        .type(EducationType.valueOf(resultSet.getString(getTableName() + ".type")))
        .enrollee(enrolleeRepository.extractEntity(resultSet))
        .id(resultSet.getInt(getTableName() + ".id"))
        .certificates(getCertificates(resultSet.getInt(getTableName() + ".id")))
        .specialties(getSpecialities(resultSet.getInt(getTableName() + ".id")))
        .build();
  }

  private List<CtCertificate> getCertificates(final Integer universityApplicationId) throws SQLException {
    try (final Connection connection = connectionPool.getConnection()) {
      final List<CtCertificate> ctCertificates = new ArrayList<>();

      final String sqlQuery = "SELECT * FROM " + getTableName() + "_certificates WHERE university_application_id = ?";
      try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        preparedStatement.setInt(1, universityApplicationId);

        final ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
          final int id = result.getInt("certificates_id");
          ctCertificateRepository.findById(connection, id).ifPresent(ctCertificates::add);
        }

        return ctCertificates;
      }
    }
  }

  private void saveCertificates(final Connection connection,
                                final Integer universityApplicationId,
                                final List<CtCertificate> ctCertificates) throws SQLException {
    final String deleteQuery = "DELETE FROM " + getTableName() + "_certificates WHERE university_application_id = ?";

    try (final PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
      preparedStatement.setInt(1, universityApplicationId);
      preparedStatement.execute();
    }

    for (final CtCertificate ctCertificate : ctCertificates) {
      final String insertQuery = "INSERT INTO " + getTableName() + "_certificates VALUES(?, ?)";
      try (final PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
        insertStatement.setInt(1, universityApplicationId);
        insertStatement.setInt(2, ctCertificate.getId());
      }
    }
  }

  private List<Specialty> getSpecialities(final Integer universityApplicationId) throws SQLException {
    try (final Connection connection = connectionPool.getConnection()) {
      final List<Specialty> specialties= new ArrayList<>();

      final String sqlQuery = "SELECT * FROM " + getTableName() + "_specialties WHERE university_application_id = ?";
      try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        preparedStatement.setInt(1, universityApplicationId);

        final ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
          final Specialty specialty = Specialty.valueOf(result.getString("specialties"));
          specialties.add(specialty);
        }

        return specialties;
      }
    }
  }

  private void saveSpecialities(final Connection connection,
                                final Integer universityApplicationId,
                                final List<Specialty> specialties) throws SQLException {
    final String deleteQuery = "DELETE FROM " + getTableName() + "_specialties WHERE university_application_id = ?";

    try (final PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
      preparedStatement.setInt(1, universityApplicationId);
      preparedStatement.execute();
    }

    for (final Specialty specialty : specialties) {
      final String insertQuery = "INSERT INTO " + getTableName() + "_specialties VALUES(?, ?)";
      try (final PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
        insertStatement.setInt(1, universityApplicationId);
        insertStatement.setString(2, specialty.toString());
      }
    }
  }

  @Override
  protected void setId(final PreparedStatement preparedStatement,
                       final Integer id, final int index) throws SQLException {
    preparedStatement.setInt(index, id);
  }

  @Override
  protected Integer getId(final ResultSet generatedKeys) throws SQLException {
    return generatedKeys.getInt(1);
  }

  @Override
  public UniversityApplication update(final Connection connection,
                                      final UniversityApplication entity) throws ResourceNotFoundException, SQLException {
    connection.setAutoCommit(false);
    try {
      final UniversityApplication application = super.update(connection, entity);
      saveCertificates(connection, entity.getId(), entity.getCertificates());
      saveSpecialities(connection, entity.getId(), entity.getSpecialties());
      connection.commit();
      return application;
    } catch (Exception e) {
      connection.rollback();
      throw e;
    }
  }

  @Override
  public UniversityApplication create(final Connection connection, final UniversityApplication entity) throws SQLException {
    connection.setAutoCommit(false);
    try {
      final UniversityApplication application = super.create(connection, entity);
      saveCertificates(connection, application.getId(), application.getCertificates());
      saveSpecialities(connection, application.getId(), application.getSpecialties());
      connection.commit();
      return application;
    } catch (Exception e) {
      connection.rollback();
      throw e;
    }
  }

  @Override
  protected int initializeModificationQuery(final PreparedStatement preparedStatement,
                                            final UniversityApplication entity) throws SQLException {
    preparedStatement.setDate(1, new Date(entity.getDate().getTime()));
    preparedStatement.setInt(2, entity.getEducationDocument().getId());
    preparedStatement.setString(3, entity.getType().toString());
    preparedStatement.setInt(4, entity.getEnrollee().getId());
    return 4;
  }
}
