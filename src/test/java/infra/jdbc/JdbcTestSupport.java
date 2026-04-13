package infra.jdbc;

import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class JdbcTestSupport {
    private static final String PROPERTIES_PATH = "jdbc-test.properties";
    private static final String DROP_ALL_OBJECTS_SQL = "DROP ALL OBJECTS";

    private JdbcTestSupport() {
    }

    public static JdbcConnectionManager connectionManager() {
        return JdbcConnectionManager.fromClasspathProperties(PROPERTIES_PATH);
    }

    public static void clearAll(JdbcConnectionManager connectionManager) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DROP_ALL_OBJECTS_SQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new JdbcRepositoryException("테스트 데이터베이스 초기화에 실패했습니다.", e);
        }
    }
}
