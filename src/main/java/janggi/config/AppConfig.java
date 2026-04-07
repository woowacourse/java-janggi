package janggi.config;

import janggi.GameRunner;
import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;
import janggi.db.TransactionManager;
import janggi.repository.GamePieceDaoImpl;
import janggi.repository.GameRepository;
import janggi.repository.GameStateDaoImpl;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import org.h2.jdbcx.JdbcConnectionPool;

public class AppConfig implements AutoCloseable {

    private static final String INVALID_PROPERTIES = "[ERROR] db.properties 파일을 찾을 수 없습니다.";

    private final JdbcConnectionPool connectionPool;
    private final DatabaseInitializer databaseInitializer;
    private final GameRunner gameRunner;

    public AppConfig() {
        this.connectionPool = createConnectionPool();
        ConnectionManager connectionManager = new ConnectionManager(connectionPool);
        this.databaseInitializer = new DatabaseInitializer(connectionManager);
        this.gameRunner = createGameRunner(connectionManager);
    }

    public GameRunner gameRunner() {
        return gameRunner;
    }

    public DatabaseInitializer databaseInitializer() {
        return databaseInitializer;
    }

    private JdbcConnectionPool createConnectionPool() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getResourceAsStream("/db.properties")) {
            validateProperties(input);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return JdbcConnectionPool.create(url, user, password);
    }

    private void validateProperties(InputStream input) {
        if (input == null) {
            throw new IllegalStateException(INVALID_PROPERTIES);
        }
    }

    private GameRunner createGameRunner(ConnectionManager connectionManager) {
        TransactionManager transactionManager = new TransactionManager(connectionManager);
        GameRepository gameRepository = new GameRepository(
                transactionManager,
                new GameStateDaoImpl(),
                new GamePieceDaoImpl()
        );

        return new GameRunner(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                gameRepository
        );
    }

    @Override
    public void close() {
        connectionPool.dispose();
    }
}
