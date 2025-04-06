package janggi.infrastructure;

import java.sql.Connection;

public interface DatabaseConnectionProvider {

    Connection getConnection();
}
