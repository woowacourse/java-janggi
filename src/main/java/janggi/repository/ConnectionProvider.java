package janggi.repository;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();
}
