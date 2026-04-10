package domain.repository;

import domain.Game;
import domain.entity.GameRoomEntity;
import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.*;
import domain.state.ChuSide;
import domain.state.HanSide;
import domain.state.Side;
import domain.state.GameState;
import persistence.DatabaseConnector;
import persistence.DatabaseInitializer;
import domain.piece.PieceFactory;

import java.sql.*;
import java.util.*;

public class JdbcGameRepository implements GameRepository {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    @Override
    public void save(Game game) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Long gameId = saveOrUpdateGameRoom(conn, game);
                resetBoard(conn, gameId);
                saveBoardState(conn, gameId, game.getBoard());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Game> load(Long gameId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            Map<Position, Piece> pieceMap = findPiecesByGameId(conn, gameId);
            if (pieceMap.isEmpty()) return Optional.empty();

            fillEmptyPositions(pieceMap);
            return findGameRoomById(conn, gameId, pieceMap);

        } catch (SQLException e) {
            throw new RuntimeException("로드 실패: " + e.getMessage(), e);
        }
    }

    public List<GameRoomEntity> findAllRooms() {
        String sql = "SELECT id, is_finished, created_at FROM game_room";
        List<GameRoomEntity> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new GameRoomEntity(
                        rs.getLong("id"),
                        rs.getBoolean("is_finished"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("목록 조회 실패", e);
        }

        return rooms;
    }

    private Long saveOrUpdateGameRoom(Connection conn, Game game) throws SQLException {
        if (game.getId() == null) {
            return insertGameRoom(conn, game);
        }
        updateGameRoom(conn, game);
        return game.getId();
    }

    private Long insertGameRoom(Connection conn, Game game) throws SQLException {
        String sql = "INSERT INTO game_room (current_turn, is_finished) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getSide().name());
            pstmt.setBoolean(2, game.isFinished());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    game.assignId(id);
                    return id;
                }
                throw new SQLException("ID 생성 실패");
            }
        }
    }

    private void updateGameRoom(Connection conn, Game game) throws SQLException {
        String sql = "UPDATE game_room SET current_turn = ?, is_finished = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, game.getSide().name());
            pstmt.setBoolean(2, game.isFinished());
            pstmt.setLong(3, game.getId());
            pstmt.executeUpdate();
        }
    }

    private void resetBoard(Connection conn, Long gameId) throws SQLException {
        String sql = "DELETE FROM board_state WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    private void saveBoardState(Connection conn, Long gameId, Map<Position, Piece> board) throws SQLException {
        String sql = "INSERT INTO board_state (game_id, row_pos, col_pos, piece_type, side) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Piece piece = entry.getValue();
                if (piece.getType() == PieceType.EMPTY) continue;

                pstmt.setLong(1, gameId);
                pstmt.setInt(2, entry.getKey().row());
                pstmt.setInt(3, entry.getKey().col());
                pstmt.setString(4, piece.getType().name());
                pstmt.setString(5, piece.getSide().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private Map<Position, Piece> findPiecesByGameId(Connection conn, Long gameId) throws SQLException {
        String sql = "SELECT * FROM board_state WHERE game_id = ?";
        Map<Position, Piece> pieceMap = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Position pos = Position.of(rs.getInt("col_pos"), rs.getInt("row_pos"));
                    Piece piece = PieceFactory.create(rs.getString("piece_type"), rs.getString("side"));
                    pieceMap.put(pos, piece);
                }
            }
        }
        return pieceMap;
    }

    private Optional<Game> findGameRoomById(Connection conn, Long gameId, Map<Position, Piece> pieceMap) throws SQLException {
        String sql = "SELECT * FROM game_room WHERE id = ? AND is_finished = FALSE";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(assembleGame(rs, gameId, pieceMap));
                }
            }
        }
        return Optional.empty();
    }

    private Game assembleGame(ResultSet rs, Long gameId, Map<Position, Piece> pieceMap) throws SQLException {
        Side turnSide = Side.valueOf(rs.getString("current_turn"));
        GameState gameState = turnSide == Side.CHU ? new ChuSide() : new HanSide();

        Game game = new Game(new Board(new DatabaseInitializer(pieceMap).initialize()), gameState);
        game.assignId(gameId);
        return game;
    }

    private void fillEmptyPositions(Map<Position, Piece> map) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                map.putIfAbsent(Position.of(i, j), EmptyPiece.getInstance());
            }
        }
    }
}
