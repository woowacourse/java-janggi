package janggi;

import janggi.console.GamePlayConsole;
import janggi.console.GameResultConsole;
import janggi.console.GameSetupConsole;
import janggi.console.GameStatus;
import janggi.dao.GameDao;
import janggi.domain.game.Game;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SystemView;

public class Application {

    private final InputView inputView = new InputView();
    private final SystemView systemView = new SystemView();
    private final BoardView boardView = new BoardView();

    private final GameDao gameDao = new GameDao();

    private final GameSetupConsole gameSetupConsole = new GameSetupConsole(inputView, systemView, boardView, gameDao);
    private final GamePlayConsole gamePlayConsole = new GamePlayConsole(inputView, systemView, boardView);
    private final GameResultConsole gameResultConsole = new GameResultConsole(systemView);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        gameSetupConsole.displayGameDescriptions();
        gameSetupConsole.selectGame();

        Game game = gameSetupConsole.setupGame();
        gameSetupConsole.displayGameSetup(game);

        GameStatus gameStatus = GameStatus.PLAYING;
        while (!gameStatus.isGameFinished()) {
            gameStatus = gamePlayConsole.playTurn(game);
        }

        gameResultConsole.displayGameResult(gameStatus);
    }
}
