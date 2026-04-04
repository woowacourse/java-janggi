package repository.dao;

import domain.PieceId;
import domain.piece.Piece;
import java.sql.Connection;
import java.util.List;

public interface PieceDao {
    void initTable(Connection connection);

    List<Piece> findAll(Connection connection);

    List<PieceId> saveAll(Connection connection, List<Piece> targets);

//    PieceId save(Connection connection, Piece entity);

//    Piece find(Connection connection, PieceId id);

}
