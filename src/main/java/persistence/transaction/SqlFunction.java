package persistence.transaction;

import java.sql.SQLException;

public interface SqlFunction<Connection, T> {

    T apply(Connection connection) throws SQLException;
}
