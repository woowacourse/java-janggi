package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static ConnectionProvider fromFile(Path path) {
        createParentDirectory(path);
        return new ConnectionProvider("jdbc:h2:file:" + path.toAbsolutePath(), "sa", "");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 데이터베이스 연결에 실패했습니다.", exception);
        }
    }

    private static void createParentDirectory(Path path) {
        Path parent = path.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (IOException exception) {
            throw new IllegalStateException("[ERROR] 데이터베이스 디렉터리 생성에 실패했습니다.", exception);
        }
    }
}
