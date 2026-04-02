package config;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
