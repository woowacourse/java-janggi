package persistence;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlConnectionSource {

    Connection getConnection() throws SQLException;
}
