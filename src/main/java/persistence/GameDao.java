package persistence;

import domain.Game;
import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.*;
import domain.state.ChuSide;
import domain.state.HanSide;
import domain.state.Side;
import domain.state.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GameDao {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    public void save(Game game) {
        String saveGameSql = "INSERT INTO game_room (id, current_turn, is_finished) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE current_turn = VALUES(current_turn), is_finished = VALUES(is_finished)";

        String deleteBoardSql = "DELETE FROM board_state WHERE game_id = ?";
        String insertBoardSql = "INSERT INTO board_state (game_id, row_pos, col_pos, piece_type, side) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(saveGameSql)) {
                pstmt.setLong(1, game.getId());
                pstmt.setString(2, game.getSide().name());
                pstmt.setBoolean(3, game.isFinished());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(deleteBoardSql)) {
                pstmt.setLong(1, game.getId());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(insertBoardSql)) {
                for (Map.Entry<Position, Piece> entry : game.getBoard().entrySet()) {
                    Piece piece = entry.getValue();
                    if (piece.getType() == PieceType.EMPTY) continue;

                    pstmt.setLong(1, game.getId());
                    pstmt.setInt(2, entry.getKey().row());
                    pstmt.setInt(3, entry.getKey().col());
                    pstmt.setString(4, piece.getType().name());
                    pstmt.setString(5, piece.getSide().name());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패: " + e.getMessage(), e);
        }
    }

    public Game load(Long gameId) {
        Map<Position, Piece> pieceMap = new HashMap<>();
        String boardSql = "SELECT * FROM board_state WHERE game_id = ?";
        String gameSql = "SELECT * FROM game_room WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(boardSql)) {
                pstmt.setLong(1, gameId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Position pos = Position.of(rs.getInt("col_pos"), rs.getInt("row_pos"));
                    Piece piece = PieceFactory.create(rs.getString("piece_type"), rs.getString("side"));
                    pieceMap.put(pos, piece);
                }
            }

            fillEmptyPositions(pieceMap);

            try (PreparedStatement pstmt = conn.prepareStatement(gameSql)) {
                pstmt.setLong(1, gameId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Side turnSide = Side.valueOf(rs.getString("current_turn"));
                    State state = turnSide == Side.CHU ? new ChuSide() : new HanSide();
                    Game game = new Game(new Board(new DbInitializer(pieceMap).initialize()), state);
                    game.assignId(gameId);
                    return game;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("로드 실패: " + e.getMessage(), e);
        }
        return null;
    }

    private void fillEmptyPositions(Map<Position, Piece> map) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                map.putIfAbsent(Position.of(i, j), EmptyPiece.getInstance());
            }
        }
    }
}
