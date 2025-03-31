package repository.connection;

import java.sql.Connection;

public interface ConnectDatabase {
    Connection create();
    void close(Connection connection);
}
