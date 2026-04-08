package janggi.infrastructure.function;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFunction<R> {
    R apply(Connection connection) throws SQLException;
}
