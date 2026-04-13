package infrastructure;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalQuery<T> {
    T execute() throws SQLException;
}
