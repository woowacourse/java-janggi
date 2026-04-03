package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.JanggiController;
import javax.sql.DataSource;
import repository.JanggiRepository;
import repository.JdbcJanggiRepository;
import service.JanggiCommandService;
import service.JanggiQueryService;

public class JanggiConfig {

    public static JanggiController setupController() {
        JanggiRepository janggiRepository = new JdbcJanggiRepository(dataSource());

        JanggiCommandService janggiCommandService = new JanggiCommandService(janggiRepository);
        JanggiQueryService janggiQueryService = new JanggiQueryService(janggiRepository);

        return new JanggiController(janggiCommandService, janggiQueryService);
    }

    private static DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/janggi_db");
        config.setUsername("root");
        config.setPassword("password");
        return new HikariDataSource(config);
    }
}
