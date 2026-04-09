package db.dao;

import db.jdbc.SqlConnection;
import db.model.MoveHistoryEntity;
import java.util.List;
import java.util.Optional;

public interface MoveHistoryDao {

    void save(SqlConnection connection, MoveHistoryEntity moveHistory);

    List<MoveHistoryEntity> findAllByGameIdOrderByMoveOrderAsc(SqlConnection connection, Long gameId);

    Optional<Integer> findLastMoveOrderByGameId(SqlConnection connection, Long gameId);
}
