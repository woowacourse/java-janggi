package janggi.repository.movement;

import janggi.config.DatabaseManager;
import janggi.domain.position.Position;
import janggi.entity.MovementEntity;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcMovementRepository implements MovementRepository {

    @Override
    public void save(Connection connection, Long gameId, Position from, Position to, PieceEntity capturedPiece) {
        String sql = """
                INSERT INTO movement (
                    janggi_game_id,
                    src_row_pos,
                    src_col_pos,
                    dest_row_pos,
                    dest_col_pos,
                    dest_team,
                    dest_type
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.setInt(2, from.row().row());
            statement.setInt(3, from.column().column());
            statement.setInt(4, to.row().row());
            statement.setInt(5, to.column().column());

            if (capturedPiece == null) {
                statement.setNull(6, java.sql.Types.VARCHAR);
                statement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                statement.setString(6, capturedPiece.dynasty());
                statement.setString(7, capturedPiece.type());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("이동 기록 저장 실패", e);
        }
    }

    @Override
    public Optional<MovementEntity> findLatestByGameId(Long gameId) {
        String sql = """
                SELECT id, src_row_pos, src_col_pos, dest_row_pos, dest_col_pos, dest_team, dest_type
                FROM movement
                WHERE janggi_game_id = ?
                ORDER BY id DESC
                LIMIT 1
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(new MovementEntity(
                        resultSet.getLong("id"),
                        gameId,
                        Position.from(resultSet.getInt("src_row_pos"), resultSet.getInt("src_col_pos")),
                        Position.from(resultSet.getInt("dest_row_pos"), resultSet.getInt("dest_col_pos")),
                        resultSet.getString("dest_team"),
                        resultSet.getString("dest_type")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("최근 이동 기록 조회 실패", e);
        }
    }

    @Override
    public void deleteById(Connection connection, Long movementId) {
        String sql = "DELETE FROM movement WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, movementId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("이동 기록 삭제 실패", e);
        }
    }

}
