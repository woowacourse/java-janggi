package domain.player;

import domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao {

    private final Connection connection;

    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    public Player insertPlayer(String playerName, int gameId, Team team) {
        String insertPlayerSql = "INSERT INTO player (name, game_id,team_color) VALUES (?,?,?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, gameId);
            preparedStatement.setString(3, team.name());
            preparedStatement.executeUpdate();

            // 새로 생성된 ID를 받아서 Player 객체에 설정
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
}
