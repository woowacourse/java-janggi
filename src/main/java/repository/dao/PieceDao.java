package repository.dao;

import java.sql.SQLException;
import java.util.List;
import repository.entity.Piece;

public interface PieceDao {
    Long save(Piece entity) throws SQLException;

    List<Long> saveAll(List<Piece> entities) throws SQLException;

    Piece find(Long id) throws SQLException;

    List<Piece> findAll() throws SQLException;
}
