package janggi;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataInitializer {

    private static final String SCHEMA_SQL = "schema.sql";
    private static final String DEMIMITER = ";";

    public static void initialize(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            InputStream inputStream = TestDataInitializer.class
                    .getClassLoader()
                    .getResourceAsStream(SCHEMA_SQL);
            String sql = new String(inputStream.readAllBytes());

            String[] sqls = sql.split(DEMIMITER);
            for (String query : sqls) {
                if (!query.trim().isEmpty()) {
                    statement.execute(query);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("schema.sql을 찾을 수 없습니다.");
        }
    }
}
