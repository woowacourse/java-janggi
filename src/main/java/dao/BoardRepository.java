package dao;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.BasicPiece;
import domain.position.Position;
import infra.db.DbConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepository {
    private static final String UPSERT_BOARD_SQL =
            "MERGE INTO board(game_id, row_idx, col_idx, team, piece_type) KEY(game_id, row_idx, col_idx) VALUES(?, ?, ?, ?, ?)";
    private static final String LOAD_BOARD_SQL =
            "SELECT row_idx, col_idx, team, piece_type FROM board WHERE game_id = ?";
    private static final String DELETE_ALL_BOARD_SQL =
            "DELETE FROM board WHERE game_id = ?";
    private static final String DELETE_MISSING_BOARD_SQL_PREFIX =
            "DELETE FROM board WHERE game_id = ? AND NOT (";
    private static final String DELETE_MISSING_BOARD_SQL_SUFFIX = ")";
    private static final String DELETE_MISSING_BOARD_SQL_OR = " OR ";
    private static final String DELETE_MISSING_BOARD_SQL_CONDITION = "(row_idx = ? AND col_idx = ?)";

    public void save(long gameId, Board board) {
        Connection connection = null;
        try {
            connection = DbConnectionFactory.createConnection();
            connection.setAutoCommit(false);
            List<Position> occupiedPositions = upsertPieces(connection, gameId, board);
            deleteMissingPieces(connection, gameId, occupiedPositions);
            connection.commit();
        } catch (SQLException e) {
            rollbackQuietly(connection);
            throw new IllegalStateException("보드 저장에 실패했습니다.", e);
        } finally {
            closeQuietly(connection);
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

    private List<Position> upsertPieces(Connection connection, long gameId, Board board) throws SQLException {
        List<Position> occupiedPositions = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(UPSERT_BOARD_SQL)) {
            for (int row = MIN_ROW; row <= MAX_ROW; row++) {
                for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                    Position position = new Position(row, column);
                    BasicPiece piece = board.findPiece(position);
                    if (piece.isNone()) {
                        continue;
                    }
                    occupiedPositions.add(position);
                    statement.setLong(1, gameId);
                    statement.setInt(2, row);
                    statement.setInt(3, column);
                    statement.setString(4, piece.getTeam().name());
                    statement.setString(5, piece.getPieceType().name());
                    statement.addBatch();
                }
            }
            statement.executeBatch();
        }

        return occupiedPositions;
    }

    private void deleteMissingPieces(Connection connection, long gameId, List<Position> occupiedPositions) throws SQLException {
        String sql = createDeleteMissingSql(occupiedPositions.size());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            int parameterIndex = 2;
            for (Position position : occupiedPositions) {
                statement.setInt(parameterIndex++, position.row());
                statement.setInt(parameterIndex++, position.column());
            }
            statement.executeUpdate();
        }
    }

    private String createDeleteMissingSql(int occupiedCount) {
        if (occupiedCount == 0) {
            return DELETE_ALL_BOARD_SQL;
        }

        StringBuilder sql = new StringBuilder(DELETE_MISSING_BOARD_SQL_PREFIX);
        for (int i = 0; i < occupiedCount; i++) {
            if (i > 0) {
                sql.append(DELETE_MISSING_BOARD_SQL_OR);
            }
            sql.append(DELETE_MISSING_BOARD_SQL_CONDITION);
        }
        sql.append(DELETE_MISSING_BOARD_SQL_SUFFIX);
        return sql.toString();
    }

    public Map<Position, BasicPiece> loadBoard(long gameId) {
        Map<Position, BasicPiece> board = initializeBoard();

        try (Connection connection = DbConnectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(LOAD_BOARD_SQL)) {
            statement.setLong(1, gameId);

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("row_idx");
                    int column = resultSet.getInt("col_idx");
                    String teamName = resultSet.getString("team");
                    String pieceTypeName = resultSet.getString("piece_type");

                    Position position = new Position(row, column);
                    BasicPiece piece = createPieceFromDb(teamName, pieceTypeName);
                    board.put(position, piece);
                }
            }
            return board;
        } catch (SQLException e) {
            throw new IllegalStateException("보드 불러오기에 실패했습니다.", e);
        }
    }

    private Map<Position, BasicPiece> initializeBoard() {
        Map<Position, BasicPiece> board = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), domain.piece.None.getInstance());
            }
        }
        return board;
    }

    private BasicPiece createPieceFromDb(String teamName, String pieceTypeName) {
        domain.player.Team team = domain.player.Team.valueOf(teamName);
        domain.piece.PieceType pieceType = domain.piece.PieceType.valueOf(pieceTypeName);
        return pieceType.createPiece(team);
    }
}
