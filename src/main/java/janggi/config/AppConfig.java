package janggi.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import janggi.GameManager;
import janggi.domain.repository.JanggiRepository;
import janggi.infrastructure.JdbcJanggiRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import javax.sql.DataSource;

public class AppConfig {
    public GameManager gameManager() {
        return new GameManager(outputView(), inputView(), janggiRepository());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private JanggiRepository janggiRepository() {
        return new JdbcJanggiRepository(dataSource());
    }

    private DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(System.getenv("DB_URL"));
        dataSource.setUser(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        return dataSource;
    }
}
