package janggi.dao.h2;

import janggi.dao.JdbcDataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2DataSource implements JdbcDataSource {
    private static final String SQL_DIR = "janggi.sql";
    private static final String URL = "jdbc:h2:~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final JdbcConnectionPool connectionPool;

    public H2DataSource() {
        this.connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
        init();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    private void init() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            InputStream resourceAsStream = readFile();

            String sql = new BufferedReader(new InputStreamReader(resourceAsStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new IllegalStateException("DB 초기화 실패", e);
        }
    }

    private InputStream readFile() {
        InputStream resourceAsStream = H2DataSource.class
                .getClassLoader()
                .getResourceAsStream(SQL_DIR);

        if (resourceAsStream == null) {
            throw new IllegalStateException("파일 찾기 실패~");
        }
        return resourceAsStream;
    }
}
