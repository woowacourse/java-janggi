package dao;

import db.DbConnectionFactory;
import domain.board.Board;
import domain.piece.BasicPiece;
import domain.position.Position;

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
    private static final String UPDATE_BOARD_SQL =
            "UPDATE board SET team = ?, piece_type = ? WHERE game_id = ? AND row_idx = ? AND col_idx = ?";
    private static final String NONE_VALUE = "NONE";

    public void saveFullBoard(long gameId, Board board) {
        Connection connection = null;
        try {
            connection = DbConnectionFactory.createConnection();
            connection.setAutoCommit(false);
            saveFullBoard(connection, gameId, board);
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            throw new IllegalStateException("보드 저장에 실패했습니다.", e);
        } finally {
            closeQuietly(connection);
        }
    }

    public void saveFullBoard(Connection connection, long gameId, Board board) {
        try {
            insertPieces(connection, gameId, board);
        } catch (SQLException e) {
            throw new IllegalStateException("보드 저장에 실패했습니다.", e);
        }
    }

    public void updateMove(Connection connection, long gameId, Position source, Position destination, BasicPiece movingPiece) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOARD_SQL)) {
            addUpdateBatch(statement, gameId, source, NONE_VALUE, NONE_VALUE);
            addUpdateBatch(statement, gameId, destination, resolveTeam(movingPiece), resolvePieceType(movingPiece));
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("보드 이동 저장에 실패했습니다.", e);
        }
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

    private void insertPieces(Connection connection, long gameId, Board board) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_BOARD_SQL)) {
            for (Position pos : collectAllPositions()) {
                addPieceBatch(statement, gameId, pos, board.findPiece(pos));
            }
            statement.executeBatch();
        }
    }

    private void addPieceBatch(PreparedStatement statement, long gameId, Position position, BasicPiece piece) throws SQLException {
        setPositionParams(statement, gameId, position);
        setPieceParams(statement, piece);
        statement.addBatch();
    }

    private void setPositionParams(PreparedStatement statement, long gameId, Position position) throws SQLException {
        statement.setLong(1, gameId);
        statement.setInt(2, position.row());
        statement.setInt(3, position.column());
    }

    private void setPieceParams(PreparedStatement statement, BasicPiece piece) throws SQLException {
        if (piece.isNone()) {
            setNonePieceParams(statement);
            return;
        }
        statement.setString(4, piece.getTeam().name());
        statement.setString(5, piece.getPieceType().name());
    }

    private void setNonePieceParams(PreparedStatement statement) throws SQLException {
        statement.setString(4, NONE_VALUE);
        statement.setString(5, NONE_VALUE);
    }

    private void addUpdateBatch(PreparedStatement statement, long gameId, Position position, String team, String pieceType) throws SQLException {
        statement.setString(1, team);
        statement.setString(2, pieceType);
        statement.setLong(3, gameId);
        statement.setInt(4, position.row());
        statement.setInt(5, position.column());
        statement.addBatch();
    }

    private String resolveTeam(BasicPiece piece) {
        if (piece.isNone()) {
            return NONE_VALUE;
        }
        return piece.getTeam().name();
    }

    private String resolvePieceType(BasicPiece piece) {
        if (piece.isNone()) {
            return NONE_VALUE;
        }
        return piece.getPieceType().name();
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

    private List<Position> collectAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                positions.add(new Position(row, column));
            }
        }
        return positions;
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

    private void closeQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private void rollbackQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
