package janggi.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import janggi.GameRunner;
import janggi.domain.repository.JanggiRepository;
import janggi.infrastructure.JdbcJanggiRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;

public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private static final String DB_URL = "DB_URL";
    private static final String DB_USER = "DB_USER";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    public GameRunner gameManager() {
        return new GameRunner(outputView(), inputView(), janggiRepository());
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

        dataSource.setURL(System.getenv(DB_URL));
        dataSource.setUser(System.getenv(DB_USER));
        dataSource.setPassword(System.getenv(DB_PASSWORD));
        try {
            logger.info("DB 연결을 시작합니다. URL : {}", System.getenv(DB_URL));
            dataSource.getConnection().close();
            logger.info("DB 연결에 성공했습니다.");
            return dataSource;
        } catch (SQLException e) {
            logger.error("DB 연결 중, 에러가 발생했습니다. 원인: {}", e.getMessage(), e);
            throw new RuntimeException("서비스 연결에 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }
}
