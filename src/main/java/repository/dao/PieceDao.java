package repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import repository.entity.Piece;

public interface PieceDao {
    Long save(Connection connection, Piece entity) throws SQLException;

    List<Long> saveAll(Connection connection, List<Piece> entities) throws SQLException;

    Piece find(Connection connection, Long id) throws SQLException;

    List<Piece> findAll(Connection connection) throws SQLException;
}
