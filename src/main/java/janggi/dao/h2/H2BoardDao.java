package janggi.dao.h2;

import janggi.dao.BoardDao;
import janggi.dao.JdbcDataSource;
import janggi.dao.entity.BoardEntity;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class H2BoardDao implements BoardDao {
    private final JdbcDataSource jdbcDataSource;

    public H2BoardDao(JdbcDataSource jdbcDataSource) {
        this.jdbcDataSource = jdbcDataSource;
    }

    @Override
    public void save(BoardEntity board) {
        String deleteSql = "DELETE FROM BOARD WHERE game_id=?";
        String insertSql = """
                INSERT INTO BOARD (GAME_ID, PIECE_ID, SIDE, X, Y)
                SELECT ?, p.id, ?, ?, ?
                FROM PIECE p
                WHERE p.PIECE_TYPE = ?
                """;

        try (Connection conn = jdbcDataSource.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            deleteStmt.setInt(1, board.gameId());
            deleteStmt.executeUpdate();

            Map<Point, Piece> pieces = board.board();

            for (Entry<Point, Piece> entry : pieces.entrySet()) {
                Point point = entry.getKey();
                Piece piece = entry.getValue();

                insertStmt.setInt(1, board.gameId());

                insertStmt.setString(2, piece.getSide().name());
                insertStmt.setInt(3, point.x());
                insertStmt.setInt(4, point.y());
                insertStmt.setString(5, piece.getPieceType().name());

                insertStmt.addBatch();
            }

            insertStmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(
                    "Board 저장에 실패했습니다. board.gameId: " + board.gameId(), e);
        }
    }

    @Override
    public BoardEntity getByGameId(int gameId) {
        String sql = """
                SELECT p.piece_type, b.side, b.x, b.y
                FROM BOARD b
                JOIN PIECE p
                ON b.PIECE_ID = p.ID
                WHERE b.GAME_ID = ?
                """;

        try (Connection conn = jdbcDataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();

            Map<Point, Piece> pieces = new HashMap<>();

            while (rs.next()) {
                PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                Side side = Side.valueOf(rs.getString("side"));

                Piece piece = pieceType.createPiece(side);
                Point point = new Point(
                        rs.getInt("x"),
                        rs.getInt("y")
                );

                pieces.put(point, piece);
            }

            return new BoardEntity(gameId, pieces);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Board 찾기에 실패했습니다. board.gameId: " + gameId, e);
        }
    }
}
