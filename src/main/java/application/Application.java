package application;

import repository.GameRepository;
import repository.JdbcGameRepository;
import view.InputView;
import view.OutputView;

public class Application {
    private static final String DB_URL = "jdbc:h2:./janggi-db";

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameRepository repository = new JdbcGameRepository(DB_URL);

        GameStarter starter = new GameStarter(inputView, outputView, repository);
        GameSession gameSession = new GameSession(inputView, outputView, repository);

        GameController gameController = new GameController(starter, gameSession);
        gameController.run();
    }
}
