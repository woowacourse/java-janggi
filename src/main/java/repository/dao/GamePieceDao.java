package repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import repository.entity.GamePiece;

public interface GamePieceDao {
    Long save(Connection connection, GamePiece entity) throws SQLException;

    List<Long> saveAll(Connection connection, List<GamePiece> entities) throws SQLException;

    GamePiece find(Connection connection, Long id) throws SQLException;

    List<GamePiece> findAll(Connection connection) throws SQLException;

    void update(Connection connection, GamePiece newEntity) throws SQLException;
}
