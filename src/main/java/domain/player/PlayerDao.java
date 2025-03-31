package domain.player;

import domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    private final Connection connection;

    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    public Player insertPlayer(String playerName, int gameId, Team team) {
        final var insertPlayerSql = "INSERT INTO player (name, game_id,team_color) VALUES (?,?,?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, gameId);
            preparedStatement.setString(3, team.name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int generatedId = 0;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            return new Player(generatedId, playerName, team);
        } catch (SQLException sqlException) {
            throw new IllegalArgumentException("플레이어 저장 오류");
        }
    }

    public Players selectPlayersByGameId(int gameId) {
        final String query = "SELECT * FROM player WHERE game_id = ? ORDER BY player_id ASC";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Player> playerList = new ArrayList<>();
            while (resultSet.next()) {
                playerList.add(new Player(
                        resultSet.getInt("player_id"),
                        resultSet.getString("name"),
                        Team.fromString(resultSet.getString("team_color"))
                ));
            }

            if (playerList.size() == 2) {
                return new Players(List.of(playerList.getFirst(), playerList.getLast()));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("플레이어 정보 조회 오류", e);
        }
        return null;
    }

}
