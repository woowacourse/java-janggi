package janggi.config;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.jdbcx.JdbcDataSource;

public class DataSourceConfig {

    private static final String URL = "jdbc:h2:~/janggi;AUTO_SERVER=TRUE";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";

    public static DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL(URL);
        dataSource.setUser(USER_NAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public static class DatabaseInitializer {

        private static final String SCHEMA_SQL = "schema.sql";
        private static final String DELIMITER = ";";

        public static void initialize(DataSource dataSource) {
            String sql = loadSql();
            executeSql(dataSource, sql);
        }

        private static String loadSql() {
            try (InputStream inputStream = DatabaseInitializer.class
                    .getClassLoader()
                    .getResourceAsStream(SCHEMA_SQL)) {

                if (inputStream == null) {
                    throw new RuntimeException(SCHEMA_SQL + "을 찾을 수 없습니다.");
                }
                return new String(inputStream.readAllBytes());

            } catch (IOException e) {
                throw new RuntimeException("SQL 파일을 읽는 중 오류가 발생했습니다.", e);
            }
        }

        private static void executeSql(DataSource dataSource, String sql) {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                for (String query : sql.split(DELIMITER)) {
                    if (!query.trim().isEmpty()) {
                        statement.execute(query);
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("데이터베이스 초기화 중 오류가 발생했습니다.", e);
            }
        }
    }
}
