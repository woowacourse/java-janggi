package janggi.repository;

import janggi.config.DatabaseManager;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcMovementRepository implements MovementRepository {

    @Override
    public void save(Long gameId, Position from, Position to) {
        String sql = """
                INSERT INTO movement (
                    janggi_game_id,
                    src_row_pos,
                    src_col_pos,
                    dest_row_pos,
                    dest_col_pos
                ) VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);
            statement.setInt(2, from.row().row());
            statement.setInt(3, from.column().column());
            statement.setInt(4, to.row().row());
            statement.setInt(5, to.column().column());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("이동 기록 저장 실패", e);
        }
    }

}
