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
import java.util.Scanner;
import org.h2.jdbcx.JdbcConnectionPool;

public class AppConfig implements AutoCloseable {

    private static final String URL = "jdbc:h2:file:./data/janggi";
    private static final String USER = "stark";
    private static final String PASSWORD = "stark123!";

    private final JdbcConnectionPool connectionPool;
    private final DatabaseInitializer databaseInitializer;
    private final GameRunner gameRunner;

    public AppConfig() {
        this.connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
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
