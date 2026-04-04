package janggi.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2DataSourceFactory {

    private static final int POOL_SIZE = 10;

    private H2DataSourceFactory() {}

    public static DataSource create() {
        Properties properties = new Properties();
        try (InputStream inputStream = H2DataSourceFactory.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("[ERROR] application.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            JdbcConnectionPool connectionPool = JdbcConnectionPool.create(url, username, password);
            connectionPool.setMaxConnections(POOL_SIZE);
            return connectionPool;
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] DB 설정 파일을 읽는데 실패했습니다.", e);
        }
    }
}
