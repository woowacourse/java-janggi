package domain.player;

import database.DbConnection;
import domain.Team;
import domain.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    private final DbConnection dbConnection;

    public PlayerDao() {
        this.dbConnection = DbConnection.getInstance();
    }

    public Player insertPlayer(String playerName, int gameId, Team team) {
        final String insertPlayerSql = "INSERT INTO player (name, game_id, team_color) VALUES (?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, gameId);
            preparedStatement.setString(3, team.name());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Player(generatedKeys.getInt(1), playerName, team);
                } else {
                    throw new DatabaseException("플레이어 ID 생성 실패");
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("플레이어 저장 오류", sqlException);
        }
    }

    public Players selectPlayersByGameId(int gameId) {
        final String query = "SELECT * FROM player WHERE game_id = ? ORDER BY player_id ASC";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Player> playerList = new ArrayList<>();
                while (resultSet.next()) {
                    playerList.add(new Player(
                            resultSet.getInt("player_id"),
                            resultSet.getString("name"),
                            Team.fromString(resultSet.getString("team_color"))
                    ));
                }

                if (playerList.size() == 2) {
                    return new Players(playerList.get(0), playerList.get(1));
                }
                throw new DatabaseException("해당 게임방이 없습니다.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("플레이어 검색 오류", e);
        }
    }
}
