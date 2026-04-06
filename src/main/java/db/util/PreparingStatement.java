package db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparingStatement {

    PreparedStatement prepare(Connection connection, String sql) throws SQLException;
}
