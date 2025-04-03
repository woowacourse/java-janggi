package dao;

import domain.player.Player;
import domain.player.Team;
import java.sql.SQLException;
import java.util.Optional;

public class PlayerDao {

    private final ConnectionManager connectionManager;

    public PlayerDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void addPlayer(final Player player) {
        final var query = "INSERT INTO player (name, team) VALUES(?, ?)";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.name());
            preparedStatement.setString(2, player.team().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 플레이어를 DB에 추가하는 도중 에러가 발생했습니다.");
        }
    }

    public Optional<Player> findPlayerByTeam(final Team team) {
        final var query = "SELECT * FROM player WHERE team = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Player(
                        resultSet.getString("name"),
                        Team.getValue(resultSet.getString("team")))
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 해당 팀에 대한 플레이어가 존재하지 않습니다.");
        }

        return Optional.empty();
    }

    public void clear() {
        final var query = "DELETE FROM player";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 플레이어 정보 삭제 도중 에러가 발생했습니다.");
        }
    }
}
