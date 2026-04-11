package repository;

import domain.piece.Side;
import janggigame.GameMetaData;
import janggigame.JanggiGameStatus;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public interface JanggiGameRepository {
    GameMetaData save(GameMetaData gameMetaData);

    Optional<GameMetaData> findLatestUnfinishedGame();

    void updateGameStatusById(Long gameId, JanggiGameStatus newStatus);
    void updateGameStatusById(Long gameId, JanggiGameStatus newStatus, Connection connection);

    void updateJangGunCountById(Map<Side, Integer> jangGunCount, Long gameId);
    void updateJangGunCountById(Map<Side, Integer> jangGunCount, Long gameId, Connection connection);

    void updateTurnById(Side currentTurnSide, Long gameId);
    void updateTurnById(Side currentTurnSide, Long gameId, Connection connection);
}
