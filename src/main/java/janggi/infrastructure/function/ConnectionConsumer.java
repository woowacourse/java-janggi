package janggi.infrastructure.function;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionConsumer {
    void execute(Connection connection) throws SQLException;
}
