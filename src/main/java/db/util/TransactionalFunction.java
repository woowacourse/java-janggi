package db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalFunction<T> {

    T execute(Connection connection, PreparedStatement statement) throws SQLException;
}
