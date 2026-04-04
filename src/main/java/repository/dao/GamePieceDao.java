package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.GamePiece;

public interface GamePieceDao {
    Long save(GamePiece entity) throws SQLException;

    List<Long> saveAll(List<GamePiece> entities) throws SQLException;

    GamePiece find(Long id) throws SQLException;

    List<GamePiece> findAll() throws SQLException;

    void update(GamePiece newEntity) throws SQLException;
}
