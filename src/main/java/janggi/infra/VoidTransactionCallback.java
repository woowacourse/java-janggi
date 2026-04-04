package janggi.infra;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface VoidTransactionCallback {
    void doInTransaction(Connection connection) throws SQLException;
}
