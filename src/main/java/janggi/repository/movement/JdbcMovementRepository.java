package janggi.repository.movement;

import janggi.config.DatabaseManager;
import janggi.domain.position.Position;
import janggi.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcMovementRepository implements MovementRepository {

    @Override
    public void save(Long gameId, Position from, Position to, PieceEntity capturedPiece) {
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

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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

}
