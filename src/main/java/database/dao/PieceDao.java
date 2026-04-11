package database.dao;

import domain.piece.Piece;
import domain.position.Position;
import java.sql.Connection;
import java.util.Map;

public interface PieceDao {
    void saveAll(int gameId, Map<Position, Piece> pieces);

    Map<Position, Piece> findAll(int gameId);

    void updatePosition(Connection connection, int gameId, Position src, Position dest);

    void delete(Connection connection, int gameId, Position position);
}
