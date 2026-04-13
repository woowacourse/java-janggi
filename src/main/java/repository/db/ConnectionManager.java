package repository.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;

public class ConnectionManager {

    private static final ThreadLocal<Connection> THREAD_LOCAL_CONNECTION = new ThreadLocal<>();

    private ConnectionManager() {
    }

    public static void set(Connection connection) {
        THREAD_LOCAL_CONNECTION.set(connection);
    }

    public static Optional<Connection> get() {
        return Optional.ofNullable(THREAD_LOCAL_CONNECTION.get());
    }

    public static boolean isPresent() {
        return get().isPresent();
    }

    public static void remove() {
        THREAD_LOCAL_CONNECTION.remove();
    }

    public static Connection getConnection(DataSource dataSource) {
        return get().orElseGet(() -> {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] 커넥션 획득 실패", e);
            }
        });
    }

    public static void releaseConnection(Connection conn) {
        if (isPresent() || conn == null) {
            return;
        }

        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 커넥션 반납 실패", e);
        }
    }
}