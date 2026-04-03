package janggi.db;

import java.sql.Connection;

public interface ConnectionManager {

    Connection createConnection();
}
