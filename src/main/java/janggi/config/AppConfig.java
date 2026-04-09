package janggi.config;

import janggi.GameRunner;
import janggi.db.TransactionManager;
import janggi.repository.GamePieceDao;
import janggi.repository.GameRepository;
import janggi.repository.GameStateDao;
import janggi.service.GameService;
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
                new GameStateDao(),
                new GamePieceDao()
        );
        GameService gameService = new GameService(transactionManager, gameRepository);

        return new GameRunner(
                new InputView(new Scanner(System.in)),
                new OutputView(),
                gameService
        );
    }
}
