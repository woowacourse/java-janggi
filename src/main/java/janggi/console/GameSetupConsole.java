package janggi.console;

import janggi.dao.GameDao;
import janggi.domain.game.Board;
import janggi.domain.game.BoardGenerator;
import janggi.domain.game.Game;
import janggi.dto.GameSummary;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SetupOption;
import janggi.view.SystemView;
import java.util.List;

public final class GameSetupConsole {

    private static final int NEW_GAME = 0;

    private final InputView inputView;
    private final SystemView systemView;
    private final BoardView boardView;
    private final GameDao gameDao;

    public GameSetupConsole(final InputView inputView, final SystemView systemView, final BoardView boardView,
                            final GameDao gameDao) {
        this.inputView = inputView;
        this.systemView = systemView;
        this.boardView = boardView;
        this.gameDao = gameDao;
    }

    public void displayGameDescriptions() {
        systemView.displayGameDescriptions();
    }

    public int selectGameId() {
        List<GameSummary> allGames = gameDao.getAllGames();
        systemView.displayStoredGames(allGames);
        List<Integer> gameIds = allGames.stream()
                .map(GameSummary::id)
                .toList();
        String gameId = inputView.readGameId(gameIds);
        return Integer.parseInt(gameId);
    }

    public Game setupGame(final int gameId) {
        if (gameId == NEW_GAME) {
            return setupNewGame();
        }
        return null;
    }

    public void displayGameSetup(final int gameId, final Game game) {
        systemView.inGame(gameId);
        boardView.displayBoard(game);
    }

    private Game setupNewGame() {
        systemView.displaySetupMenus();
        final String input = inputView.readSetupOption();
        final Board board = BoardGenerator.generate(SetupOption.of(input));
        return new Game(board);
    }
}
