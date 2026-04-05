package janggi.dao;

import janggi.db.SQLManager;
import janggi.dto.PieceDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PieceDao {
    private static final String FAILED_TABLE_INIT_MESSAGE = "기물 테이블 생성에 실패하였습니다.";
    private static final String FAILED_PIECES_GET_MESSAGE = "전체 기물 정보를 가져오는데 실패하였습니다.";
    private static final String FAILED_PIECE_UPDATE_MESSAGE = "기물 데이터 업데이트에 실패하였습니다.";
    private static final String FAILED_PIECE_DELETE_MESSAGE = "기물 데이터 삭제에 실패하였습니다.";

    private final SQLManager sqlManager;

    public PieceDao(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void initTable() {
        String sql =
        """
        CREATE TABLE IF NOT EXISTS Piece (
            game_id INTEGER NOT NULL,
            x INTEGER NOT NULL,
            y INTEGER NOT NULL,
            piece_type TEXT NOT NULL,
            side TEXT NOT NULL,
            PRIMARY KEY (game_id, x, y),
            FOREIGN KEY (game_id) REFERENCES GameRoom(id) ON DELETE CASCADE
        )
        """;

        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_TABLE_INIT_MESSAGE);
        }
    }

    public List<PieceDto> getAllPieces(int gameId) {
        List<PieceDto> pieces = new ArrayList<>();
        String sql = "SELECT * FROM Piece WHERE game_id = ?";

        try (Connection conn = sqlManager.ensureConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PieceDto pieceDto = new PieceDto(
                            rs.getInt("x"),
                            rs.getInt("y"),
                            rs.getString("piece_type"),
                            rs.getString("side")
                    );
                    pieces.add(pieceDto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_PIECES_GET_MESSAGE);
        }
        return pieces;
    }

    public void updatePiece(Connection connection, int gameId, PieceDto pieceDto) {
        updatePieces(connection, gameId, List.of(pieceDto));
    }

    public void updatePieces(Connection connection, int gameId, List<PieceDto> pieceDtos) {
        String sql = """
        INSERT INTO Piece (game_id, x, y, piece_type, side)
        VALUES (?, ?, ?, ?, ?)
        ON CONFLICT(game_id, x, y)
        DO UPDATE SET
            piece_type = excluded.piece_type,
            side = excluded.side
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (PieceDto pieceDto : pieceDtos) {
                pstmt.setInt(1, gameId);
                pstmt.setInt(2, pieceDto.x());
                pstmt.setInt(3, pieceDto.y());
                pstmt.setString(4, pieceDto.pieceType());
                pstmt.setString(5, pieceDto.side());

                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_PIECE_UPDATE_MESSAGE);
        }
    }

    public void deletePiece(Connection connection, int gameId, int x, int y) {
        String sql = "DELETE FROM Piece WHERE game_id = ? AND x = ? AND y = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_PIECE_DELETE_MESSAGE);
        }
    }
}
