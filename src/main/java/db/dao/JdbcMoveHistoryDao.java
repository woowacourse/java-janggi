package db.dao;

import db.jdbc.SqlConnection;
import db.model.MoveHistoryEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pieces.PieceType;
import pieces.Side;

public class JdbcMoveHistoryDao implements MoveHistoryDao {

    @Override
    public void save(final SqlConnection connection, final MoveHistoryEntity moveHistory) {
        validateMoveHistory(moveHistory);

        final String sql = """
            INSERT INTO move_history (
                game_id, move_order, moving_piece_type, moving_piece_side,
                departure_row, departure_column, destination_row, destination_column,
                is_captured, captured_piece_type, captured_piece_side, created_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, moveHistory.gameId());
            statement.setInt(2, moveHistory.moveOrder());
            statement.setString(3, moveHistory.movingPieceType().name());
            statement.setString(4, moveHistory.movingPieceSide().name());
            statement.setInt(5, moveHistory.departureRow());
            statement.setInt(6, moveHistory.departureColumn());
            statement.setInt(7, moveHistory.destinationRow());
            statement.setInt(8, moveHistory.destinationColumn());
            statement.setBoolean(9, moveHistory.isCapture());

            if (moveHistory.capturedPieceType() != null) {
                statement.setString(10, moveHistory.capturedPieceType().name());
                statement.setString(11, moveHistory.capturedPieceSide().name());
            } else {
                statement.setNull(10, Types.VARCHAR);
                statement.setNull(11, Types.VARCHAR);
            }

            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("이동 기록 저장에 실패했습니다.", e);
        }
    }

    @Override
    public List<MoveHistoryEntity> findAllByGameIdOrderByMoveOrderAsc(
        final SqlConnection connection,
        final Long gameId
    ) {
        validateGameId(gameId);

        final String sql = """
            SELECT id, game_id, move_order, moving_piece_type, moving_piece_side,
                   departure_row, departure_column, destination_row, destination_column,
                   is_captured, captured_piece_type, captured_piece_side
            FROM move_history
            WHERE game_id = ?
            ORDER BY move_order ASC
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<MoveHistoryEntity> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(parseMoveHistory(resultSet));
                }
                return result;
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("이동 기록 목록 조회에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<Integer> findLastMoveOrderByGameId(
        final SqlConnection connection,
        final Long gameId
    ) {
        validateGameId(gameId);

        final String sql = """
            SELECT MAX(move_order)
            FROM move_history
            WHERE game_id = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                final int value = resultSet.getInt(1);
                return resultSet.wasNull() ? Optional.empty() : Optional.of(value);
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("마지막 이동 순서 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void deleteById(final SqlConnection connection, final Long id) {
        validateId(id);

        final String sql = """
            DELETE FROM move_history
            WHERE id = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("삭제할 이동 기록이 존재하지 않습니다. id=" + id);
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("이동 기록 삭제에 실패했습니다.", e);
        }
    }

    private void validateGameId(final Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("게임 ID가 필요합니다.");
        }
    }

    private void validateId(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException("이동 기록 ID가 필요합니다.");
        }
    }

    private void validateMoveHistory(final MoveHistoryEntity moveHistory) {
        if (moveHistory == null) {
            throw new IllegalArgumentException("저장할 이동 기록이 필요합니다.");
        }
    }

    private MoveHistoryEntity parseMoveHistory(final ResultSet resultSet) throws SQLException {
        final String capturedType = resultSet.getString("captured_piece_type");
        final String capturedSide = resultSet.getString("captured_piece_side");

        return new MoveHistoryEntity(
            resultSet.getLong("id"),
            resultSet.getLong("game_id"),
            resultSet.getInt("move_order"),
            PieceType.valueOf(resultSet.getString("moving_piece_type")),
            Side.valueOf(resultSet.getString("moving_piece_side")),
            resultSet.getInt("departure_row"),
            resultSet.getInt("departure_column"),
            resultSet.getInt("destination_row"),
            resultSet.getInt("destination_column"),
            resultSet.getBoolean("is_captured"),
            capturedType != null ? PieceType.valueOf(capturedType) : null,
            capturedSide != null ? Side.valueOf(capturedSide) : null
        );
    }
}
