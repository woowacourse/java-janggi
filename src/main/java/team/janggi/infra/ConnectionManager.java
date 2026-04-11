package team.janggi.infra;

import java.sql.Connection;

public interface ConnectionManager {
    Connection getConnection();
}
