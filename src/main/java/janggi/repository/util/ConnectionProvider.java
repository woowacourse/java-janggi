package janggi.repository.util;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();
}
