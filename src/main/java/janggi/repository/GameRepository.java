package janggi.repository;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameRepository {

    private final JdbcContext jdbcContext;

    public GameRepository(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public Optional<Integer> findActiveGameId() {
        String sql = "SELECT id FROM game WHERE is_finished = 0 ORDER BY id DESC LIMIT 1";

        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return Optional.of(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회에 실패하였습니다.");
        }
        return Optional.empty();
    }

    public int saveGame(Side turn, Map<Point, Piece> board) {
        try (Connection conn = jdbcContext.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int gameId = insertGame(conn, turn);
                insertPieces(conn, gameId, board);
                conn.commit();
                return gameId;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.");
        }
    }

    public void updateGame(int gameId, Side turn, Map<Point, Piece> board) {
        try (Connection conn = jdbcContext.getConnection()) {
            try {
                conn.setAutoCommit(false);
                updateTurn(conn, gameId, turn);
                deletePieces(conn, gameId);
                insertPieces(conn, gameId, board);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.");
        }
    }

    public Map<Point, Piece> loadPieces(int gameId) {
        String sql = "SELECT piece_type, side, x, y FROM game_piece WHERE game_id = ?";
        Map<Point, Piece> board = new HashMap<>();

        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = executeQuery(pstmt, gameId)) {

            while (rs.next()) {
                PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                Side side = Side.valueOf(rs.getString("side"));
                int x = rs.getInt("x");
                int y = rs.getInt("y");

                board.put(Point.of(x, y), PieceFactory.create(pieceType, side));
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보를 불러오는 데 실패했습니다.");
        }

        return board;
    }

    public Side loadTurn(int gameId) {
        String sql = "SELECT turn FROM game WHERE id = ?";
        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = executeQuery(pstmt, gameId)) {
            if (rs.next()) {
                return Side.valueOf(rs.getString("turn"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("턴 정보를 불러오지 못했습니다.");
        }
        throw new RuntimeException("턴 정보를 불러오지 못했습니다.");
    }

    public void finish(int gameId, Side winner) {
        String sql = "UPDATE game SET is_finished = 1, winner =? WHERE id = ?";
        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, winner.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리에 실패하였습니다.");
        }
    }

    private ResultSet executeQuery(PreparedStatement pstmt, int param) throws SQLException {
        pstmt.setInt(1, param);
        return pstmt.executeQuery();
    }

    private int insertGame(Connection conn, Side turn) throws SQLException {
        String sql = "INSERT INTO game (turn) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();

            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new RuntimeException("게임 저장에 실패했습니다.");
        }
    }

    private void insertPieces(Connection conn, int gameId, Map<Point, Piece> board) throws SQLException {
        String sql = "INSERT INTO game_piece (game_id, piece_type, side, x, y) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Point, Piece> entry : board.entrySet()) {
                Point point = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setInt(1, gameId);
                pstmt.setString(2, piece.getType().name());
                pstmt.setString(3, piece.getSide().name());
                pstmt.setInt(4, point.x());
                pstmt.setInt(5, point.y());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        }
    }

    private void updateTurn(Connection conn, int gameId, Side turn) throws SQLException {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, turn.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        }
    }

    private void deletePieces(Connection conn, int gameId) throws SQLException {
        String sql = "DELETE FROM game_piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        }
    }
}
