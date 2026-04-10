package janggi.repository.util.connection;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();
}
