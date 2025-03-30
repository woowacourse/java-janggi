package janggi.console;

import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.service.GameService;
import janggi.view.SystemView;

public final class GameExitConsole {

    private static final int NEW_GAME = 0;

    private final SystemView systemView;
    private final GameService gameService;

    public GameExitConsole(final SystemView systemView, final GameService gameService) {
        this.systemView = systemView;
        this.gameService = gameService;
    }

    public void saveGame(final int gameId, final Game game) {
        if (gameId == NEW_GAME) {
            gameService.saveGame(game);
        }
    }

    public void displayGameResult(final GameStatus gameStatus) {
        if (gameStatus == GameStatus.ENDED) {
            systemView.outGame();
        }
        if (gameStatus == GameStatus.CHO_WIN) {
            systemView.win(Team.CHO);
        }
        if (gameStatus == GameStatus.HAN_WIN) {
            systemView.win(Team.HAN);
        }
    }
}
