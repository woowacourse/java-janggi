package janggi.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DefaultDatabaseProvider implements DatabaseConnectionProvider {

    private static final String CONFIG_PATH = "config.properties";

    private static DefaultDatabaseProvider instance;

    private DefaultDatabaseProvider() {
    }

    public static DefaultDatabaseProvider getInstance() {
        if (instance == null) {
            instance = new DefaultDatabaseProvider();
        }
        return instance;
    }

    public final Connection getConnection() {
        final Properties properties = new Properties();
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIG_PATH)) {
            properties.load(input);

            final String server = properties.getProperty("db.server");
            final String database = properties.getProperty("db.name");
            final String options = properties.getProperty("db.options");
            final String username = properties.getProperty("db.username");
            final String password = properties.getProperty("db.password");

            return DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + options, username, password);
        } catch (final SQLException | IOException e) {
            throw new IllegalStateException("[ERROR] 데이터베이스 연결 또는 설정 파일 로딩에 실패했습니다.");
        }
    }
}
