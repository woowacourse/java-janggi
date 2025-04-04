package db.connector;

import java.sql.Connection;

public interface Connector {
    Connection getConnection();
}
