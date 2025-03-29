package repository;

import domain.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public final class RoomDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public RoomDAO(final Connector connector) {
        this.connector = connector;
    }

    public void create(final Room room) {
        final String query = "INSERT INTO room (is_active) VALUES (?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, room.isActive());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deactivateRoom(final Room room) {
        final String query = "UPDATE room SET is_active = false WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.id());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsActiveRoomById(final int id) {
        final String query = "SELECT EXISTS(SELECT 1 FROM room WHERE id = ? AND is_active=true)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getBoolean(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNextId() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS last_id FROM room");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return incrementLastId(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int incrementLastId(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return counter.addAndGet(resultSet.getInt("last_id"));
        }
        return counter.get();
    }
}
