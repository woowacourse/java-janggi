package janggi.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractDatabaseConnectionProvider implements DatabaseConnectionProvider {

    private final String configPath;

    public AbstractDatabaseConnectionProvider(final String configPath) {
        this.configPath = configPath;
    }

    @Override
    public Connection getConnection() {
        final Properties properties = new Properties();
        try (InputStream input = ProductionDatabaseProvider.class.getClassLoader().getResourceAsStream(configPath)) {
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
