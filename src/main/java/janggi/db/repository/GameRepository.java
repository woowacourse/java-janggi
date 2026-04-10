package janggi.db.repository;

import janggi.domain.board.MoveResult;
import janggi.domain.game.JanggiGame;

import java.util.List;

public interface GameRepository {

    Long save(JanggiGame game);

    void updateGame(Long gameId, JanggiGame game, MoveResult result);

    JanggiGame load(Long gameId);

    List<Long> findAllGameIds();

    void delete(Long gameId);
}
