package repository.dao;

import domain.piece.Piece;
import domain.PieceId;
import java.sql.Connection;
import java.util.List;

public interface PieceDao {
    void initTable(Connection connection);

    PieceId save(Connection connection, Piece entity);

    List<PieceId> saveAll(Connection connection, List<Piece> entities);

    Piece find(Connection connection, PieceId id);

    List<Piece> findAll(Connection connection);
}
