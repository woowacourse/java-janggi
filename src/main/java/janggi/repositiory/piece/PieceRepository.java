package janggi.repositiory.piece;

import janggi.domain.piece.Piece;
import janggi.domain.vo.position.Position;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface PieceRepository {
    void updateALL(Connection conn, BoardSnapshot boardSnapshot) throws SQLException;

    Map<Position, Piece> findAll(Connection conn, Long gameId);

    default Map<Position, Piece> findAll(DataSource ds, Long gameId) {
        try (Connection conn = ds.getConnection()) {
            return findAll(conn, gameId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
