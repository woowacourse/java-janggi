package repository;

import domain.piece.Side;
import janggigame.GameMetaData;
import janggigame.JangGunCount;
import janggigame.JanggiGameStatus;

import java.sql.Connection;
import java.util.Optional;

public interface JanggiGameRepository {
    GameMetaData save(GameMetaData gameMetaData);

    Optional<GameMetaData> findLatestUnfinishedGame();

    void updateGameStatusById(Long gameId, JanggiGameStatus newStatus);
    void updateGameStatusById(Long gameId, JanggiGameStatus newStatus, Connection connection);

    void updateJangGunCountById(Long gameId, JangGunCount jangGunCount);
    void updateJangGunCountById(Long gameId, JangGunCount jangGunCount, Connection connection);

    void updateTurnById(Long gameId, Side currentTurnSide);
    void updateTurnById(Long gameId, Side currentTurnSide, Connection connection);
}
