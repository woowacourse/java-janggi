package db.dao;

import core.GameStatus;
import db.jdbc.SqlConnection;
import db.model.GameEntity;
import java.util.List;
import java.util.Optional;
import core.Turn;

public interface GameDao {

    Long save(SqlConnection connection, GameEntity game);

    Optional<GameEntity> findById(SqlConnection connection, Long id);

    List<GameEntity> findTop10OrderByCreatedAtDesc(SqlConnection connection);

    void updateState(SqlConnection connection, Long gameId, Turn turn, GameStatus gameStatus);
}
