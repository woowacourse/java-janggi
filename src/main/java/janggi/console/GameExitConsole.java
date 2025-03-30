package janggi.console;

import janggi.dao.GameDao;
import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.dto.PieceDto;
import janggi.dto.PieceDtoMapper;
import janggi.view.SystemView;
import java.util.List;

public final class GameExitConsole {

    private static final int NEW_GAME = 0;

    private final SystemView systemView;
    private final GameDao gameDao;

    public GameExitConsole(final SystemView systemView, final GameDao gameDao) {
        this.systemView = systemView;
        this.gameDao = gameDao;
    }

    public void saveGame(final int gameId, final Game game) {
        if (gameId == NEW_GAME) {
            List<PieceDto> pieceDtos = PieceDtoMapper.toPieceDtos(game);
            gameDao.saveGame(game.getTurn(), pieceDtos);
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
