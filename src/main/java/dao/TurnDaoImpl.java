package dao;

import domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDaoImpl implements TurnDao {

    private final Connection connection;

    public TurnDaoImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Team load() {
        final String query = "SELECT turn FROM turns LIMIT 1";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String turn = resultSet.getString("turn");
                return Team.valueOf(turn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(Team turn) {
        final String query = "INSERT INTO turn VALUES(?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(final Team turn) {
        final String query = "UPDATE turn SET turn = ? WHERE turn = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.setString(2, turn.inverse().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        final String query = "DELETE FROM turn";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
