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
import java.util.List;

public class BoardRepository {
    private static final String UPSERT_BOARD_SQL =
            "MERGE INTO board(game_id, row_idx, col_idx, team, piece_type) KEY(game_id, row_idx, col_idx) VALUES(?, ?, ?, ?, ?)";

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
            return "DELETE FROM board WHERE game_id = ?";
        }

        StringBuilder sql = new StringBuilder("DELETE FROM board WHERE game_id = ? AND NOT (");
        for (int i = 0; i < occupiedCount; i++) {
            if (i > 0) {
                sql.append(" OR ");
            }
            sql.append("(row_idx = ? AND col_idx = ?)");
        }
        sql.append(')');
        return sql.toString();
    }
}
