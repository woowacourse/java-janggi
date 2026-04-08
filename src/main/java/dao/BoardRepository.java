package dao;

import domain.board.Board;
import domain.piece.BasicPiece;
import domain.position.Position;
import db.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.Constants.*;

public class BoardRepository {
    private static final String INSERT_BOARD_SQL =
            "INSERT INTO board(game_id, row_idx, col_idx, team, piece_type) VALUES(?, ?, ?, ?, ?)";
    private static final String LOAD_BOARD_SQL =
            "SELECT row_idx, col_idx, team, piece_type FROM board WHERE game_id = ?";
    private static final String DELETE_ALL_BOARD_SQL =
            "DELETE FROM board WHERE game_id = ?";
    private static final String NONE_VALUE = "NONE";

    public void save(long gameId, Board board) {
        Connection connection = null;
        try {
            connection = DbConnectionFactory.createConnection();
            connection.setAutoCommit(false);
            save(connection, gameId, board);
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            throw new IllegalStateException("보드 저장에 실패했습니다.", e);
        } finally {
            closeQuietly(connection);
        }
    }

    public void save(Connection connection, long gameId, Board board) {
        try {
            deleteAllPieces(connection, gameId);
            insertPieces(connection, gameId, board);
        } catch (SQLException e) {
            throw new IllegalStateException("보드 저장에 실패했습니다.", e);
        }
    }

    private void closeQuietly(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private void rollbackQuietly(Connection connection) {
        if (connection == null) return;
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void deleteAllPieces(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BOARD_SQL)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private void insertPieces(Connection connection, long gameId, Board board) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOARD_SQL)) {
            for (Position pos : collectAllPositions()) {
                addPieceBatch(statement, gameId, pos, board.findPiece(pos));
            }
            statement.executeBatch();
        }
    }

    private void addPieceBatch(PreparedStatement statement, long gameId, Position position, BasicPiece piece) throws SQLException {
        statement.setLong(1, gameId);
        statement.setInt(2, position.row());
        statement.setInt(3, position.column());
        if (piece.isNone()) {
            statement.setString(4, NONE_VALUE);
            statement.setString(5, NONE_VALUE);
            statement.addBatch();
            return;
        }
        statement.setString(4, piece.getTeam().name());
        statement.setString(5, piece.getPieceType().name());
        statement.addBatch();
    }

    private List<Position> collectAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                positions.add(new Position(row, column));
            }
        }
        return positions;
    }

    public Map<Position, BasicPiece> loadBoard(long gameId) {
        Map<Position, BasicPiece> board = initializeBoard();
        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_BOARD_SQL)) {
            statement.setLong(1, gameId);
            loadPiecesFromResultSet(statement.executeQuery(), board);
            return board;
        } catch (SQLException e) {
            throw new IllegalStateException("보드 불러오기에 실패했습니다.", e);
        }
    }

    private void loadPiecesFromResultSet(java.sql.ResultSet resultSet, Map<Position, BasicPiece> board) throws SQLException {
        while (resultSet.next()) {
            Position position = toPosition(resultSet);
            BasicPiece piece = createPieceFromDb(resultSet.getString("team"), resultSet.getString("piece_type"));
            board.put(position, piece);
        }
    }

    private Position toPosition(java.sql.ResultSet resultSet) throws SQLException {
        return new Position(resultSet.getInt("row_idx"), resultSet.getInt("col_idx"));
    }

    private BasicPiece createPieceFromDb(String teamName, String pieceTypeName) {
        if (isNoneValue(teamName) || isNoneValue(pieceTypeName)) {
            return domain.piece.None.getInstance();
        }
        return domain.piece.PieceType.valueOf(pieceTypeName)
                .createPiece(domain.player.Team.valueOf(teamName));
    }

    private boolean isNoneValue(String value) {
        return NONE_VALUE.equals(value);
    }

    private Map<Position, BasicPiece> initializeBoard() {
        Map<Position, BasicPiece> board = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            initializeRow(board, row);
        }
        return board;
    }

    private void initializeRow(Map<Position, BasicPiece> board, int row) {
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            board.put(new Position(row, column), domain.piece.None.getInstance());
        }
    }
}
