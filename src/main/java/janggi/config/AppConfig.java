package janggi.config;

import janggi.GameRunner;
import janggi.db.TransactionManager;
import janggi.repository.GamePieceDaoImpl;
import janggi.repository.GameRepository;
import janggi.repository.GameStateDaoImpl;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Scanner;

public class AppConfig {

    private final GameRunner gameRunner;

    public AppConfig(TransactionManager transactionManager) {
        this.gameRunner = createGameRunner(transactionManager);
    }

    public GameRunner gameRunner() {
        return gameRunner;
    }

    private GameRunner createGameRunner(TransactionManager transactionManager) {
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
}
