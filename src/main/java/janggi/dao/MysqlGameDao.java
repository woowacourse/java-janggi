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

public final class MysqlGameDao implements GameDao {
    
    @Override
    public List<GameDto> findAllGames(final Connection connection) {
        String selectQuery = "SELECT id, turn, created_at FROM game ORDER BY created_at";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<GameDto> games = new ArrayList<>();
            while (resultSet.next()) {
                games.add(toGameDto(resultSet));
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameDto findGameById(final Connection connection, final int gameId) {
        String selectQuery = "SELECT id, turn, created_at FROM game WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

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

    @Override
    public int addGame(final Connection connection, final Team turn) {
        String insertQuery = "INSERT INTO game (turn) VALUES(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
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

    @Override
    public void updateGameById(final Connection connection, final int gameId, final Team turn) {
        String updateQuery = "UPDATE game SET turn = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, turn.name());
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGameById(final Connection connection, final int id) {
        String deleteQuery = "DELETE FROM game WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

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
