package janggi.repository.mysql;

import janggi.repository.ConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionProvider implements ConnectionProvider {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                    USERNAME,
                    PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }
}
