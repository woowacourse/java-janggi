package repository;

import domain.board.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import vo.Location;

public final class LocationDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public LocationDAO(final Connector connector) {
        this.connector = connector;
    }

    public void create(final Location location) {
        final String query = "INSERT INTO location (piece_type, piece_row, piece_column, player_id) VALUES (?,?,?,?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, location.getPiece());
            preparedStatement.setInt(2, location.getRow());
            preparedStatement.setInt(3, location.getColumn());
            preparedStatement.setInt(4, location.getPlayerId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocationAt(final Point point) {
        final String deleteQuery = "DELETE FROM location WHERE piece_row = ? AND piece_column = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, point.row());
            deleteStatement.setInt(2, point.column());
            deleteStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLocation(final Point from, final Point to) {
        final String updateQuery = "UPDATE location SET piece_row = ?, piece_column = ? WHERE piece_row = ? AND piece_column = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, to.row());
            updateStatement.setInt(2, to.column());
            updateStatement.setInt(3, from.row());
            updateStatement.setInt(4, from.column());
            updateStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
