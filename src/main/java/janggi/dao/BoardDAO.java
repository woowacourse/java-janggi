package janggi.dao;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.factory.PieceFactory;
import janggi.manager.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardDAO {

    private static final String SELECT_BOARD_QUERY = "SELECT PIECE_NAME, TEAM, POSITION_ROW, POSITION_COLUMN FROM PIECE WHERE GAME_ROOM_NAME = ?";
    private static final String INSERT_PIECE_QUERY = "INSERT INTO PIECE(PIECE_NAME, TEAM, POSITION_ROW, POSITION_COLUMN, GAME_ROOM_NAME) VALUES (?, ?, ?, ?, ?)";
    private static final String MOVE_PIECE_QUERY = "UPDATE PIECE SET POSITION_ROW = ?, POSITION_COLUMN = ? WHERE POSITION_ROW = ? AND POSITION_COLUMN = ?";
    private static final String DELETE_PIECE_QUERY = "DELETE FROM PIECE WHERE POSITION_ROW = ? AND POSITION_COLUMN = ?";

    private final DatabaseManager databaseManager;

    public BoardDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Board toDomain(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(SELECT_BOARD_QUERY);
            pstmt.setString(1, gameRoomName);
            ResultSet rs = pstmt.executeQuery();

            return toDomain(rs);
        } catch (SQLException e) {
            throw new RuntimeException("toDomain 중 오류 발생", e);
        }
    }

    private Board toDomain(ResultSet rs) {
        Map<Position, Piece> board = new HashMap<>();

        try {
            while (rs.next()) {
                PieceType pieceType = PieceType.find(rs.getString("PIECE_NAME"));
                Team team = Team.valueOf(rs.getString("TEAM"));
                int row = rs.getInt("POSITION_ROW");
                int column = rs.getInt("POSITION_COLUMN");

                Position position = Position.of(row, column);

                Piece piece = PieceFactory.create(pieceType, team);

                board.put(position, piece);
            }

            return new Board(board);
        } catch (SQLException e) {
            throw new RuntimeException("toDomain 중 오류 발생", e);
        }
    }

    public void saveAll(String gameRoomName, Board board) {
        Map<Position, Piece> pieces = board.getPieceMap();

        for (Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            save(gameRoomName, position, piece);
        }

    }

    private void save(String gameRoomName, Position position, Piece piece) {

        try (Connection connection = databaseManager.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(INSERT_PIECE_QUERY);
            pstmt.setString(1, piece.getName());
            pstmt.setString(2, piece.getTeam().toString());
            pstmt.setInt(3, position.getRow());
            pstmt.setInt(4, position.getColumn());
            pstmt.setString(5, gameRoomName);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("save 중 오류 발생", e);
        }
    }

    public void movePiece(Position currentPosition, Position targetPosition) {
        try (Connection conn = databaseManager.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement deleteStmt = conn.prepareStatement(DELETE_PIECE_QUERY);

            deleteStmt.setInt(1, targetPosition.getRow());
            deleteStmt.setInt(2, targetPosition.getColumn());

            deleteStmt.executeUpdate();

            PreparedStatement moveStmt = conn.prepareStatement(MOVE_PIECE_QUERY);

            moveStmt.setInt(1, targetPosition.getRow());
            moveStmt.setInt(2, targetPosition.getColumn());
            moveStmt.setInt(3, currentPosition.getRow());
            moveStmt.setInt(4, currentPosition.getColumn());
            moveStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("movePiece 중 오류 발생", e);
        }
    }
}
