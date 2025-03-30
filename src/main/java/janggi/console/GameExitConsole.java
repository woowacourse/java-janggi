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

    public void saveOrDeleteGame(final GameStatus gameStatus, final int gameId, final Game game) {
        if (gameStatus == GameStatus.ENDED) {
            saveGame(gameId, game);
            return;
        }
        if (gameStatus == GameStatus.CHO_WIN || gameStatus == GameStatus.HAN_WIN) {
            deleteGame(gameId);
            return;
        }
        throw new IllegalStateException("[ERROR] 게임이 비정상 종료되었습니다.");
    }

    private void saveGame(final int gameId, final Game game) {
        if (gameId == NEW_GAME) {
            gameService.saveGame(game);
            return;
        }
        gameService.updateGame(gameId, game);
    }

    private void deleteGame(final int gameId) {
        if (gameId != NEW_GAME) {
            gameService.deleteGame(gameId);
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
