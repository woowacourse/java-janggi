package repository;

import domain.player.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class PlayerRepository implements Repository<Player> {
    private final Connector connector;

    public PlayerRepository(final Connector connector) {
        this.connector = connector;
    }

    @Override
    public void create(final Player player) {
        final String query = "INSERT INTO player(team,score,is_turn) VALUES(?, ?, ?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getTeam().toString());
            preparedStatement.setDouble(2, player.getScore().value());
            preparedStatement.setBoolean(3, player.isTurn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(final Player user) {

    }

    @Override
    public Player findById(int id) {
        return null;
    }

    @Override
    public List<Player> findAllByKey(int key) {
        return List.of();
    }
}
