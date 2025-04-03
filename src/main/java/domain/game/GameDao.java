package domain.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    private final Connection connection;

    public GameDao(Connection connection) {
        this.connection = connection;
    }

    public void insertGame(int gameId) {
        final var insertGameSql = "INSERT INTO games (game_id,game_status) VALUES (?,?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertGameSql)) {

            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, Status.CREATED.name());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 저장 오류", e);
        }
    }

    public void updateGame(String status, int gameId, int bluePlayerId, int redPlayerId, int thisTurnSequence) {
        final var updateGameSql = "UPDATE games SET game_status = ?, player1_id = ?, player2_id = ?, current_turn = ? WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(updateGameSql)) {

            preparedStatement.setString(1,
                    status);
            preparedStatement.setInt(2, bluePlayerId);
            preparedStatement.setInt(3, redPlayerId);
            preparedStatement.setInt(4, thisTurnSequence);
            preparedStatement.setInt(5, gameId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("게임아이디를 찾을 수 없습니다. " + gameId);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 업데이트 오류", e);
        }
    }

    public void updateGameSequence(int gameId, int thisTurnSequence) {
        final var updateGameSql = "UPDATE games SET current_turn = ? WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(updateGameSql)) {

            preparedStatement.setInt(1, thisTurnSequence);
            preparedStatement.setInt(2, gameId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("게임아이디를 찾을 수 없습니다. " + gameId);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 업데이트 오류", e);
        }
    }

    public Games selectGameById(int gameId) {
        final var query = "SELECT * FROM games WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Games(
                        resultSet.getInt("game_id"),
                        resultSet.getString("game_status"),
                        resultSet.getInt("current_turn"),
                        resultSet.getInt("player1_id"),
                        resultSet.getInt("player2_id")
                );
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 불러오기 오류", e);
        }
        return null;
    }
}
