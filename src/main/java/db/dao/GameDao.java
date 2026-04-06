package db.dao;

import core.GameStatus;
import db.model.GameEntity;
import java.util.List;
import java.util.Optional;
import participant.Turn;

public interface GameDao {

    Long save(GameEntity game);

    Optional<GameEntity> findById(Long id);

    List<GameEntity> findTop10OrderByCreatedAtDesc();

    void updateState(Long gameId, Turn turn, GameStatus gameStatus);
}
