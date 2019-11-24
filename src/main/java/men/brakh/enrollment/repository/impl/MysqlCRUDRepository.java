package men.brakh.enrollment.repository.impl;

import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.repository.CRUDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class MysqlCRUDRepository<T extends BaseEntity, I> implements CRUDRepository<T, I> {
  private static final Logger logger = LoggerFactory.getLogger(MysqlCRUDRepository.class);

  private final String tableName;
  protected final ConnectionPool connectionPool;

  public MysqlCRUDRepository(final ConnectionPool connectionPool, final String tableName) {
    this.connectionPool = connectionPool;
    this.tableName = tableName;
  }

  public abstract List<String> getSqlFields();

  public abstract T extractEntity(final ResultSet resultSet) throws SQLException;

  protected abstract void setId(final PreparedStatement preparedStatement, final I id, final int index) throws SQLException;

  protected abstract I getId(final ResultSet generatedKeys) throws SQLException;

  /**
   * Initialize create/update query (Fields have to bee ine the same order as in method getSqlFields())
   * @return number of last index.
   */
  protected abstract int initializeModificationQuery(final PreparedStatement preparedStatement,
                                                     final T entity) throws SQLException;

  /**
   * Can be overriden in case of joins
   */
  protected String getSelectQuery() {
    return "SELECT " + getSqlFieldsAsString() +" FROM " + tableName;
  }


  private List<T> extractList(final ResultSet resultSet) throws SQLException {
    final List<T> list = new ArrayList<>();

    while(resultSet.next()) {
      list.add(extractEntity(resultSet));

    }

    return list;
  }

  public String getTableName() {
    return tableName;
  }

  public String getSqlFieldsAsString() {
    return getSqlFields()
        .stream()
        .map(field -> getTableName() + "." + field)
        .collect(Collectors.joining(", "));
  }

  @Override
  public T create(final T entity) {
    try (final Connection connection = connectionPool.getConnection()) {
      return create(connection, entity);
    } catch (SQLException e) {
      logger.error("SQL error", e);
      throw new RuntimeException("SQL error", e);
    }
  }

  public T create(final Connection connection, final T entity) throws SQLException {
    final String set = getSqlFields()
        .stream()
        .filter(fieildName -> !fieildName.equals("id"))
        .map(fieldName -> "`" + fieldName + "`")
        .collect(Collectors.joining(", "));

    final String questions = getSqlFields()
        .stream()
        .filter(fieildName -> !fieildName.equals("id"))
        .map(fieldName -> "?")
        .collect(Collectors.joining(", "));



    final String sqlQuery = "INSERT INTO " + getTableName() + " (" + set + ") VALUES (" + questions + ")";

    try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
      initializeModificationQuery(preparedStatement, entity);

      preparedStatement.executeUpdate();

      final T copy = (T) entity.clone();
      final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

      if (generatedKeys.next()) {
        final I id = getId(generatedKeys);
        copy.setId(id);
      } else {
        throw new SQLException("Creating user failed, no ID obtained.");
      }
      return copy;
    }
  }

  @Override
  public void delete(final I id) {
    try (final Connection connection = connectionPool.getConnection()) {
      delete(connection, id);
    } catch (SQLException e) {
      logger.error("SQL error", e);
      throw new RuntimeException("SQL error", e);
    }
  }

  public void delete(final Connection connection, final I id) throws SQLException {
    final String sqlQuery = "DELETE FROM " + getTableName() + " WHERE id = ?";
    try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      setId(preparedStatement, id, 1);
      preparedStatement.execute();
    }
  }
  @Override
  public Optional<T> findById(final I id) {
    try (final Connection connection = connectionPool.getConnection()) {
      return findById(connection, id);
    } catch (SQLException e) {
      logger.error("SQL error", e);
      return Optional.empty();
    }
  }

  public Optional<T> findById(final Connection connection, final I id) throws SQLException {
    final String condition = tableName + ".id = ?";
    final boolean isJoinQuery = getSelectQuery().toLowerCase().contains(" join ");
    final String sqlQuery = getSelectQuery() + (isJoinQuery ? " AND " : " WHERE " )  + condition;
    try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      setId(preparedStatement, id, 1);

      final ResultSet result = preparedStatement.executeQuery();
      if (result.next()) {
        return Optional.of(extractEntity(result));
      } else {
        return Optional.empty();
      }
    }
  }

  @Override
  public List<T> findAll() {
    try (final Connection connection = connectionPool.getConnection()) {
      return findAll(connection);
    } catch (SQLException e) {
      logger.error("SQL error", e);
      return Collections.emptyList();
    }
  }

  public List<T> findAll(final Connection connection) throws SQLException {
    final String sqlQuery = getSelectQuery();

    try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      final ResultSet result = preparedStatement.executeQuery();

      return extractList(result);
    }
  }

  @Override
  public T update(final T entity) throws ResourceNotFoundException {
    try (final Connection connection = connectionPool.getConnection()) {
      return update(connection, entity);
    } catch (SQLException e) {
      logger.error("SQL error", e);
      throw new ResourceNotFoundException();
    }
  }

  public T update(final Connection connection, final T entity) throws ResourceNotFoundException, SQLException {
    final String set = getSqlFields()
        .stream()
        .filter(fieildName -> !fieildName.equals("id"))
        .map(fieldName -> "`" + fieldName + "` = ?")
        .collect(Collectors.joining(", "));

    final String sqlQuery = "UPDATE " + getTableName() + " SET " + set + " WHERE id = ?";

    try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      final int lastIndex = initializeModificationQuery(preparedStatement, entity);
      setId(preparedStatement, (I) entity.getId(), lastIndex + 1);

      preparedStatement.execute();

      return entity;
    }
  }


  public List<T> find(final String sqlQuery, final Consumer<PreparedStatement> statementConsumer) {
    try (final Connection connection = connectionPool.getConnection()) {
      try (final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        statementConsumer.accept(preparedStatement);

        final ResultSet result = preparedStatement.executeQuery();
        return extractList(result);
      }
    } catch (SQLException e) {
      return Collections.emptyList();
    }
  }
}
