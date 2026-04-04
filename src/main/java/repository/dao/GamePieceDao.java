package repository.dao;

import java.sql.Connection;
import java.util.List;
import repository.entity.GamePiece;

public interface GamePieceDao {
    void initTable(Connection connection);

    Long save(Connection connection, GamePiece entity);

    List<Long> saveAll(Connection connection, List<GamePiece> entities);

    GamePiece find(Connection connection, Long id);

    List<GamePiece> findAll(Connection connection);

    void update(Connection connection, GamePiece newEntity);
}
