package repository;

import domain.board.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import vo.Location;

public final class LocationDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public LocationDAO(final Connector connector) {
        this.connector = connector;
    }

    public void createBatch(final List<Location> locations) {
        final String query = "INSERT INTO location (location_piece, location_row, location_column, player_id) "
                + "VALUES (?,?,?,?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Location location : locations) {
                preparedStatement.setString(1, location.getPiece());
                preparedStatement.setInt(2, location.getRow());
                preparedStatement.setInt(3, location.getColumn());
                preparedStatement.setInt(4, location.getPlayerId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocationAt(final Point point, final int gameId) {
        final String deleteQuery = "DELETE l FROM location l JOIN player pl ON l.player_id = pl.id "
                + "WHERE l.location_row = ? AND l.location_column = ? AND pl.game_id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, point.row());
            deleteStatement.setInt(2, point.column());
            deleteStatement.setInt(3, gameId);
            deleteStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLocation(final Point from, final Point to, final int gameId) {
        final String updateQuery = "UPDATE location l JOIN player pl ON l.player_id = pl.id "
                + "SET l.location_row = ?, l.location_column = ? "
                + "WHERE l.location_row = ? AND l.location_column = ? AND pl.game_id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, to.row());
            updateStatement.setInt(2, to.column());
            updateStatement.setInt(3, from.row());
            updateStatement.setInt(4, from.column());
            updateStatement.setInt(5, gameId);
            updateStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
