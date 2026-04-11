package repository;

import domain.piece.Side;
import domain.janggigame.Game;
import domain.janggigame.JangGunCount;
import domain.janggigame.GameStatus;

import java.sql.Connection;
import java.util.Optional;

public interface JanggiGameRepository {
    Game save(Game game);

    Optional<Game> findLatestUnfinishedGame();

    void updateGameStatusById(Long gameId, GameStatus newStatus);
    void updateGameStatusById(Long gameId, GameStatus newStatus, Connection connection);

    void updateJangGunCountById(Long gameId, JangGunCount jangGunCount);
    void updateJangGunCountById(Long gameId, JangGunCount jangGunCount, Connection connection);

    void updateTurnById(Long gameId, Side currentTurnSide);
    void updateTurnById(Long gameId, Side currentTurnSide, Connection connection);
}
