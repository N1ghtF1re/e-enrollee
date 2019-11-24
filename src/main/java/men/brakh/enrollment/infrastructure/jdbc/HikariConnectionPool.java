package men.brakh.enrollment.infrastructure.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionPool implements ConnectionPool {
  private static HikariDataSource ds;

  static {
    HikariConfig config = new HikariConfig("/db.properties");
    ds = new HikariDataSource(config);
  }

  @Override
  public Connection getConnection() throws SQLException {
    return ds.getConnection();
  }
}
