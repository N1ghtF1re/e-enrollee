package men.brakh.enrollment.infrastructure.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
  Connection getConnection() throws SQLException;
}
