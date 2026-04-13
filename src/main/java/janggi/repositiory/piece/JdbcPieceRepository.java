package janggi.repositiory.piece;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JdbcPieceRepository implements PieceRepository {
    private final JdbcDataSource dataSource;

    public JdbcPieceRepository(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void updateALL(BoardSnapshot boardSnapshot) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                deleteALL(conn, boardSnapshot.gameId());
                saveAll(conn, boardSnapshot.gameId(), boardSnapshot.pieces());
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("업데이트 중 오류 발생, 롤백합니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteALL(Connection conn, Long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    private void saveAll(Connection conn, Long gameId, Map<Position, Piece> map) throws SQLException {
        String sql = "INSERT INTO piece(game_id, row_index, col_index, piece_type, team) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : map.entrySet()) {
                Position pos = entry.getKey();
                Piece piece = entry.getValue();

                if (piece.isEmpty()) {
                    continue;
                }

                pstmt.setLong(1, gameId);
                pstmt.setInt(2, pos.getRow());
                pstmt.setInt(3, pos.getCol());
                pstmt.setString(4, piece.pieceType().getCode());
                pstmt.setString(5, piece.getTeam().getCode());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    @Override
    public Map<Position, Piece> findAll(Long gameId) {
        String sql = "select * from piece where game_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);

            ResultSet rs = pstmt.executeQuery();

            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Position position = new Position(rs.getInt("row_index"), rs.getInt("col_index"));
                Piece piece = PieceFactory.create(PieceType.fromCode(rs.getString("piece_type")),
                        Team.fromCode(rs.getString("team")));

                pieces.put(position, piece);
            }

            return pieces;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
