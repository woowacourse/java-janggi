package janggi;

import janggi.console.GameExitConsole;
import janggi.console.GamePlayConsole;
import janggi.console.GameSetupConsole;
import janggi.console.GameStatus;
import janggi.dao.MysqlGameDao;
import janggi.dao.MysqlPieceDao;
import janggi.domain.game.Game;
import janggi.service.GameService;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SystemView;

public class Application {

    private final InputView inputView = new InputView();
    private final SystemView systemView = new SystemView();
    private final BoardView boardView = new BoardView();

    private final MysqlGameDao mysqlGameDao = new MysqlGameDao();
    private final MysqlPieceDao mysqlPieceDao = new MysqlPieceDao();

    private final GameService gameService = new GameService(mysqlGameDao, mysqlPieceDao);

    private final GameSetupConsole gameSetupConsole = new GameSetupConsole(inputView, systemView, boardView,
            gameService);
    private final GamePlayConsole gamePlayConsole = new GamePlayConsole(inputView, systemView, boardView);
    private final GameExitConsole gameExitConsole = new GameExitConsole(systemView, gameService);

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
        gameExitConsole.displayGameResult(gameStatus);
        gameExitConsole.saveOrDeleteGame(gameStatus, gameId, game);
    }
}
