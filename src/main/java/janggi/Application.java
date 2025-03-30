package janggi;

import janggi.console.GameExitConsole;
import janggi.console.GamePlayConsole;
import janggi.console.GameSetupConsole;
import janggi.console.GameStatus;
import janggi.dao.GameDao;
import janggi.dao.MysqlConnection;
import janggi.dao.PieceDao;
import janggi.domain.game.Game;
import janggi.service.GameService;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SystemView;

public class Application {

    private final InputView inputView = new InputView();
    private final SystemView systemView = new SystemView();
    private final BoardView boardView = new BoardView();

    private final MysqlConnection mysqlConnection = new MysqlConnection();
    private final GameDao gameDao = new GameDao(mysqlConnection);
    private final PieceDao pieceDao = new PieceDao(mysqlConnection);

    private final GameService gameService = new GameService(gameDao, pieceDao);

    private final GameSetupConsole gameSetupConsole = new GameSetupConsole(inputView, systemView, boardView, gameDao,
            gameService);
    private final GamePlayConsole gamePlayConsole = new GamePlayConsole(inputView, systemView, boardView);
    private final GameExitConsole gameExitConsole = new GameExitConsole(systemView, gameDao);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        gameSetupConsole.displayGameDescriptions();
        int gameId = gameSetupConsole.selectGameId();
        Game game = gameSetupConsole.setupGame(gameId);
        gameSetupConsole.displayGameSetup(gameId, game);

        GameStatus gameStatus = GameStatus.PLAYING;
        while (!gameStatus.isGameFinished()) {
            gameStatus = gamePlayConsole.playTurn(game);
        }

        gameExitConsole.saveGame(gameId, game);
        gameExitConsole.displayGameResult(gameStatus);
    }
}
