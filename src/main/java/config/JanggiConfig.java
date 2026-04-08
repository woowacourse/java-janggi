package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.JanggiController;
import dao.JanggiGameDao;
import dao.JanggiGameStateDao;
import infrastructure.TransactionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import repository.JanggiRepository;
import repository.JdbcJanggiRepository;
import service.JanggiCommandService;
import service.JanggiQueryService;

public class JanggiConfig {

    public static JanggiController setupController() {
        JanggiRepository janggiRepository = setupJanggiRepository();

        JanggiCommandService janggiCommandService = new JanggiCommandService(janggiRepository);
        JanggiQueryService janggiQueryService = new JanggiQueryService(janggiRepository);

        return new JanggiController(janggiCommandService, janggiQueryService);
    }

    private static DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:file:./janggi_db;MODE=MySQL");
        config.setUsername("root");
        config.setPassword("password");
        return new HikariDataSource(config);
    }

    private static JdbcJanggiRepository setupJanggiRepository() {
        DataSource dataSource = dataSource();
        try {
            initSchema(dataSource);
        } catch (SQLException e) {
            throw new RuntimeException("스키마 초기화 실패", e);
        }
        TransactionContext.init(dataSource);
        return new JdbcJanggiRepository(
                new JanggiGameDao(),
                new JanggiGameStateDao()
        );
    }

    private static void initSchema(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("RUNSCRIPT FROM 'classpath:schema.sql'");
        }
    }
}
