package db.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionalRunnable {

    void execute(Connection connection) throws SQLException;
}
