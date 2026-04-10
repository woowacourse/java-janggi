package infra;

import domain.piece.Side;
import domain.players.Player;
import domain.players.PlayerStatus;
import repository.PlayerRepository;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCPlayerRepository implements PlayerRepository {
    @Override
    public void saveAll(Connection connection, Long gameId, List<Player> players) {
        String sql = """
                INSERT INTO player (game_id, player_status, score, side)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

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
    public void update(Connection connection, Long gameId, List<Player> players) {
        String sql = "UPDATE player SET score = ?, player_status = ? WHERE game_id = ? AND side = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

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

    @Override
    public Optional<Map<Side, Player>> findPlayersByGameId(Long gameId) {
        String selectSql = "SELECT side, score, player_status FROM player WHERE game_id = ?";

        try (Connection connection = JDBCContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Side, Player> players = new LinkedHashMap<>();
                while (resultSet.next()) {
                    Side side = Side.valueOf(resultSet.getString("side"));
                    double score = resultSet.getDouble("score");
                    PlayerStatus playerStatus = PlayerStatus.valueOf(resultSet.getString("player_status"));
                    players.put(side, Player.of(side, score, playerStatus));
                }

                return Optional.of(players);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류", e);
        }
    }
}
