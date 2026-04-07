package db.dao;

import db.model.MoveHistoryEntity;
import java.util.List;
import java.util.Optional;

public interface MoveHistoryDao {

    Long save(final MoveHistoryEntity moveHistory);

    List<MoveHistoryEntity> findAllByGameIdOrderByMoveOrderAsc(final Long gameId);

    Optional<Integer> findMaxMoveOrderByGameId(final Long gameId);

    Optional<MoveHistoryEntity> findLastByGameId(final Long gameId);

    void deleteLastByGameId(final Long gameId);
}
