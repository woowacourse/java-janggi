package application;

import static domain.piece.Camp.CHO;
import static domain.piece.Camp.HAN;

import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import repository.GameRepository;
import repository.JdbcGameRepository;
import view.GameCommand;
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
