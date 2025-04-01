package dao;

import domain.TeamType;
import domain.player.Player;
import domain.player.Username;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    public long savePlayer(Player player, Long gameId, Connection connection) {
        String query = "INSERT INTO players (game_id, name, team_type) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, player.getName());
            preparedStatement.setString(3, player.getTeamType().name());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long result = generatedKeys.getLong(1);
            generatedKeys.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public List<Player> findPlayersByGameId(Long gameId, Connection connection) {
        String query = "SELECT * FROM players WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Player> result = mapTeamPlayers(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private List<Player> mapTeamPlayers(ResultSet resultSet) {
        List<Player> players = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                TeamType teamType = TeamType.valueOf(resultSet.getString("team_type"));
                players.add(new Player(new Username(name), teamType));
            }
            return players;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }
}
