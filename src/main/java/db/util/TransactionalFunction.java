package db.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalFunction<T> {

    T execute(Connection connection) throws SQLException;
}
