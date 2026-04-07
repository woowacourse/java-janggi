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

public class AppConfig {

    private static final String URL = "jdbc:h2:file:./data/janggi";
    private static final String USER = "stark";
    private static final String PASSWORD = "stark123!";

    public GameRunner gameRunner() {
        return new GameRunner(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                gameRepository()
        );
    }

    public DatabaseInitializer databaseInitializer() {
        return new DatabaseInitializer(connectionManager());
    }

    private GameRepository gameRepository() {
        return new GameRepository(transactionManager(), new GameStateDaoImpl(), new GamePieceDaoImpl());
    }

    private TransactionManager transactionManager() {
        return new TransactionManager(connectionManager());
    }

    private ConnectionManager connectionManager() {
        return new ConnectionManager(URL, USER, PASSWORD);
    }
}
