package team.janggi.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConnectionManager implements ConnectionManager {

    private final String URL = "jdbc:h2:file:./data/janggi;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
    private final String USER = "sa";
    private final String PASSWORD = "";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
