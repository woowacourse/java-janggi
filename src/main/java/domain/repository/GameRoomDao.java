package domain.repository;

import domain.Game;
import domain.board.Board;
import domain.coordinate.Position;
import domain.entity.GameRoomEntity;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.state.ChuSide;
import domain.state.GameState;
import domain.state.HanSide;
import domain.state.Side;
import persistence.DatabaseConnector;
import persistence.DatabaseInitializer;

import java.sql.*;
import java.util.*;

public class GameRoomDao {

    private static final String INSERT_GAME_ROOM_SQL = "INSERT INTO game_room (current_turn, is_finished) VALUES (?, ?)";
    private static final String UPDATE_GAME_ROOM_SQL = "UPDATE game_room SET current_turn = ?, is_finished = ? WHERE id = ?";
    private static final String SELECT_GAME_ROOM_BY_ID_SQL = "SELECT * FROM game_room WHERE id = ? AND is_finished = FALSE";
    private static final String SELECT_ALL_GAME_ROOMS_SQL = "SELECT id, is_finished, created_at FROM game_room";

    public Long saveOrUpdateGameRoom(Connection conn, Game game) throws SQLException {
        if (game.getId() == null) {
            return insertGameRoom(conn, game);
        }
        updateGameRoom(conn, game);
        return game.getId();
    }

    public Optional<Game> findGameRoomById(Connection conn, Long gameId, Map<Position, Piece> pieceMap) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_GAME_ROOM_BY_ID_SQL)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(assembleGame(rs, gameId, pieceMap));
                }
            }
        }
        return Optional.empty();
    }

    public List<GameRoomEntity> findAllRooms() {
        List<GameRoomEntity> rooms = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_GAME_ROOMS_SQL);
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

    private Long insertGameRoom(Connection conn, Game game) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_GAME_ROOM_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_GAME_ROOM_SQL)) {
            pstmt.setString(1, game.getSide().name());
            pstmt.setBoolean(2, game.isFinished());
            pstmt.setLong(3, game.getId());
            pstmt.executeUpdate();
        }
    }

    private Game assembleGame(ResultSet rs, Long gameId, Map<Position, Piece> pieceMap) throws SQLException {
        Side turnSide = Side.valueOf(rs.getString("current_turn"));
        GameState gameState = turnSide == Side.CHU ? new ChuSide() : new HanSide();

        Game game = new Game(new Board(new DatabaseInitializer(pieceMap).initialize()), gameState);
        game.assignId(gameId);
        return game;
    }
}
