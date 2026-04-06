package janggi.db.repository;

import janggi.domain.board.MoveResult;
import janggi.domain.game.JanggiGame;

public interface GameRepository {

    Long save(JanggiGame game);

    void updateGame(Long gameId, JanggiGame game, MoveResult result);

    JanggiGame load(Long gameId);

    boolean hasOngoingGame();

    Long getLatestGameId();

    void delete(Long gameId);
}
