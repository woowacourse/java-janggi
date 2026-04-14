package janggi.repositiory;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.Statement;

public class RepositoryTest {
    protected static JdbcDataSource dataSource;

    @BeforeAll
    static void setup() {
        dataSource = new JdbcDataSource();

        dataSource.setURL("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
    }

    @AfterEach
    void clear() throws Exception {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE piece");
            stmt.execute("DELETE FROM game");
        }
    }
}
