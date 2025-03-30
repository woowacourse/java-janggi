package janggi.dao;

import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class GameDao {

    private final MysqlConnection mysqlConnection;

    public GameDao(final MysqlConnection mysqlConnection) {
        this.mysqlConnection = mysqlConnection;
    }

    public List<GameDto> findAllGames() {
        String query = "SELECT id, turn, created_at FROM game ORDER BY created_at";
        List<GameDto> games = new ArrayList<>();

        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                games.add(toGameDto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    public GameDto findGameById(final int gameId) {
        String query = "SELECT id, turn, created_at FROM game WHERE id = ?";

        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return toGameDto(resultSet);
            }
            throw new SQLException("게임을 조회할 수 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addGame(final Team turn) {
        String insertGameQuery = "INSERT INTO game (turn) VALUES(?)";

        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertGameQuery,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, turn.name());
            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new SQLException("게임 생성 후 키가 반환되지 않았습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGameById(final int gameId, final Team turn) {
        String updateQuery = "UPDATE game SET turn = ? WHERE id = ?";

        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, turn.name());
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGameById(final int id) {
        String deleteQuery = "DELETE FROM game WHERE id = ?";

        try (Connection connection = mysqlConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private GameDto toGameDto(final ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String turn = resultSet.getString("turn");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new GameDto(id, turn, createdAt.toLocalDateTime());
    }
}
