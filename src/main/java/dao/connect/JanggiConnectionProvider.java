package dao.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JanggiConnectionProvider implements ConnectionProvider {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                    USERNAME, PASSWORD);
            if (connection == null) {
                throw new SQLException("DB 연결을 실패했습니다. DB 상태를 확인해 주세요.");
            }
            return connection;
        } catch (final SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void closeConnection(final Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결을 종료하는데 실패했습니다. DB 상태를 확인해주세요.");
        }
    }
}
