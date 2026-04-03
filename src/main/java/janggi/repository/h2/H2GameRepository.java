package janggi.repository.h2;

import static janggi.repository.h2.DataSource.getConnection;

import janggi.domain.side.Side;
import janggi.entity.GameEntity;
import janggi.entity.Status;
import janggi.repository.GameRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2GameRepository implements GameRepository {

    @Override
    public void save(GameEntity game) {
        String sql = "INSERT INTO game (name, CHO_SET_UP, HAN_SET_UP, status, winner) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, game.name());
            stmt.setString(2, game.choSetUp());
            stmt.setString(3, game.hanSetUp());
            stmt.setString(4, game.status().name());
            stmt.setString(5, getWinnerName(game));

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public GameEntity findById(int id) {
        String sql = "SELECT id, name, CHO_SET_UP, HAN_SET_UP , status, winner FROM game WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToGame(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public GameEntity findByName(String name) {
        String sql = "SELECT id, name, CHO_SET_UP, HAN_SET_UP, status, winner FROM game WHERE NAME = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToGame(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM move WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private GameEntity mapResultSetToGame(ResultSet resultSet) throws SQLException {
        return new GameEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("cho_set_up"),
                resultSet.getString("han_set_up"),
                Status.valueOf(resultSet.getString("status")),
                getWinner(resultSet)
        );
    }

    private Side getWinner(ResultSet resultSet) throws SQLException {
        if (resultSet.getString("winner") == null) {
            return null;
        }

        return Side.valueOf(resultSet.getString("winner"));
    }

    private String getWinnerName(GameEntity game) {
        if (game.winner() == null) {
            return null;
        }
        return game.winner().getName();
    }

}
