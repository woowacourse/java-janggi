package db.dao;

import core.GameStatus;
import db.model.GameEntity;
import java.util.List;
import java.util.Optional;
import participant.Turn;

public interface GameDao {

    Long save(final GameEntity game);

    Optional<GameEntity> findById(final Long id);

    List<GameEntity> findTop10OrderByCreatedAtDesc();

    void updateState(final Long gameId, final Turn turn, final GameStatus gameStatus);
}
