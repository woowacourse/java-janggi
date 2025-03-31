package dao;

import entity.PlayerEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {
    private final JanggiConnection janggiConnection;

    public PlayerDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public List<PlayerEntity> getAllPlayers() {
        final var query = "SELECT * FROM player";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<PlayerEntity> players = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long teamId = resultSet.getLong("team_id");

                PlayerEntity playerEntity = new PlayerEntity(id, teamId);
                players.add(playerEntity);
            }

            return players;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
