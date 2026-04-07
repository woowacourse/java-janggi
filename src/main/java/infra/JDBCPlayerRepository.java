package infra;

import domain.players.Player;
import repository.PlayerRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCPlayerRepository implements PlayerRepository {
    @Override
    public void saveAll(Long gameId, List<Player> players) {
        String sql = """
                INSERT INTO player (game_id, player_status, score, side)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Player player : players) {
                statement.setLong(1, gameId);
                statement.setString(2, player.getPlayerStatus().name());
                statement.setDouble(3, player.getScore());
                statement.setString(4, player.getSide().name());

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("플레이어 업데이트 실패", e);
        }
    }

    @Override
    public void update(Long gameId, List<Player> players) {
        String sql = "UPDATE player SET score = ?, player_status = ? WHERE game_id = ? AND side = ?";

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Player player : players) {
                statement.setDouble(1, player.getScore());
                statement.setString(2, player.getPlayerStatus().name());
                statement.setLong(3, gameId);
                statement.setString(4, player.getSide().name());

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("플레이어 업데이트 실패", e);
        }
    }
}
