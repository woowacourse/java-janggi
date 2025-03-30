package janggi.dao.impl;

import janggi.dao.BoardDAO;
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

public class BoardDAOImpl implements BoardDAO {

    private static final String SELECT_BOARD_QUERY = "SELECT PIECE_NAME, TEAM, POSITION_ROW, POSITION_COLUMN FROM BOARD WHERE GAME_ROOM_NAME = ?";
    private static final String INSERT_PIECE_QUERY = "INSERT INTO BOARD(PIECE_NAME, TEAM, POSITION_ROW, POSITION_COLUMN, GAME_ROOM_NAME) VALUES (?, ?, ?, ?, ?)";
    private static final String MOVE_PIECE_QUERY = "UPDATE BOARD SET POSITION_ROW = ?, POSITION_COLUMN = ? WHERE POSITION_ROW = ? AND POSITION_COLUMN = ? AND GAME_ROOM_NAME = ?";
    private static final String DELETE_PIECE_QUERY = "DELETE FROM BOARD WHERE POSITION_ROW = ? AND POSITION_COLUMN = ? AND GAME_ROOM_NAME = ?";

    private final DatabaseManager databaseManager;

    public BoardDAOImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public Board toDomain(String gameRoomName) {
        try (Connection connection = databaseManager.getConnection(); PreparedStatement pstmt = connection.prepareStatement(
                SELECT_BOARD_QUERY)) {

            pstmt.setString(1, gameRoomName);
            ResultSet rs = pstmt.executeQuery();

            return toDomain(rs);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("toDomain 중 오류 발생", e);
        }
    }

    @Override
    public void save(String gameRoomName, Board board) {
        Map<Position, Piece> pieces = board.getBoard();

        try (Connection conn = databaseManager.getConnection()) {
            saveAllWithTransaction(gameRoomName, conn, pieces);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("DB 연결 중 오류 발생", e);
        }
    }

    @Override
    public void movePiece(String gameRoomName, Position currentPosition, Position targetPosition) {
        try (Connection conn = databaseManager.getConnection()) {
            movePieceWithTransaction(gameRoomName, currentPosition, targetPosition, conn);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("DB 연결 중 오류 발생", e);
        }
    }

    private Board toDomain(ResultSet resultSet) {
        try {
            Map<Position, Piece> board = new HashMap<>();

            putPieces(resultSet, board);

            return new Board(board);
        } catch (final SQLException e) {
            throw new IllegalArgumentException("toDomain 중 오류 발생", e);
        }
    }

    private void putPieces(ResultSet resultSet, Map<Position, Piece> board) throws SQLException {
        while (resultSet.next()) {
            PieceType pieceType = PieceType.find(resultSet.getString("PIECE_NAME"));
            Team team = Team.valueOf(resultSet.getString("TEAM"));
            int row = resultSet.getInt("POSITION_ROW");
            int column = resultSet.getInt("POSITION_COLUMN");

            Position position = Position.of(row, column);

            Piece piece = PieceFactory.create(pieceType, team);

            board.put(position, piece);
        }
    }

    private void saveAllWithTransaction(String gameRoomName, Connection conn, Map<Position, Piece> pieces)
            throws SQLException {
        try {
            conn.setAutoCommit(false);

            saveBoard(gameRoomName, conn, pieces);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new IllegalArgumentException("saveAll 중 오류 발생, 롤백 수행됨", e);
        }
    }

    private void saveBoard(String gameRoomName, Connection conn, Map<Position, Piece> pieces) throws SQLException {
        for (Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            save(conn, gameRoomName, position, piece);
        }
    }

    private void save(Connection conn, String gameRoomName, Position position, Piece piece) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_PIECE_QUERY)) {
            pstmt.setString(1, piece.getName());
            pstmt.setString(2, piece.getTeam().toString());
            pstmt.setInt(3, position.getRow());
            pstmt.setInt(4, position.getColumn());
            pstmt.setString(5, gameRoomName);

            pstmt.executeUpdate();
        }
    }

    private void movePieceWithTransaction(String gameRoomName, Position currentPosition, Position targetPosition,
                                          Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        try (PreparedStatement deleteStmt = conn.prepareStatement(DELETE_PIECE_QUERY);
             PreparedStatement moveStmt = conn.prepareStatement(MOVE_PIECE_QUERY)) {

            deletePosition(gameRoomName, targetPosition, deleteStmt);
            movePosition(gameRoomName, currentPosition, targetPosition, moveStmt);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new IllegalArgumentException("movePiece 중 오류 발생, 롤백 수행됨", e);
        }
    }

    private void movePosition(String gameRoomName, Position currentPosition, Position targetPosition,
                              PreparedStatement moveStmt) throws SQLException {
        moveStmt.setInt(1, targetPosition.getRow());
        moveStmt.setInt(2, targetPosition.getColumn());
        moveStmt.setInt(3, currentPosition.getRow());
        moveStmt.setInt(4, currentPosition.getColumn());
        moveStmt.setString(5, gameRoomName);
        moveStmt.executeUpdate();
    }

    private void deletePosition(String gameRoomName, Position targetPosition, PreparedStatement deleteStmt)
            throws SQLException {
        deleteStmt.setInt(1, targetPosition.getRow());
        deleteStmt.setInt(2, targetPosition.getColumn());
        deleteStmt.setString(3, gameRoomName);
        deleteStmt.executeUpdate();
    }
}
