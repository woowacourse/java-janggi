package repository.connector;

import java.sql.SQLException;

public interface TransactionalConnector extends Connector {

    void beginTransaction() throws SQLException;

    void commitTransaction() throws SQLException;

    void rollbackTransaction() throws SQLException;

    void closeTransaction() throws SQLException;

    boolean hasActiveTransaction();
}
