package janggi.dao.game;

import janggi.GameState;
import janggi.dao.entity.GameEntity;
import janggi.piece.Team;

public interface GameDao {

    GameEntity findByStatus(final GameState gameStatus);

    void addGame();

    void updateGameStatus(final Long gameId, final Team turnTeam, final double chuScore,
                          final double hanScore);

    void deleteGameBy(final Long gameId);
}
