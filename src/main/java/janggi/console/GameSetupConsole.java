package janggi.console;

import janggi.domain.game.Board;
import janggi.domain.game.BoardGenerator;
import janggi.domain.game.Game;
import janggi.dto.GameDto;
import janggi.service.GameService;
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
    private final GameService gameService;

    public GameSetupConsole(final InputView inputView, final SystemView systemView, final BoardView boardView,
                            final GameService gameService) {
        this.inputView = inputView;
        this.systemView = systemView;
        this.boardView = boardView;
        this.gameService = gameService;
    }

    public void displayGameDescriptions() {
        systemView.displayGameDescriptions();
    }

    public int selectGameId() {
        List<GameDto> allGames = gameService.getAllGames();
        systemView.displayStoredGames(allGames);
        List<Integer> gameIds = allGames.stream()
                .map(GameDto::id)
                .toList();
        String gameId = inputView.readGameId(gameIds);
        return Integer.parseInt(gameId);
    }

    public Game setupGame(final int gameId) {
        if (gameId == NEW_GAME) {
            return setupNewGame();
        }
        return gameService.loadGameByGameId(gameId);
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
