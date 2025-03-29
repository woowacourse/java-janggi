package janggi.dao;

import janggi.domain.piece.Side;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GameDao {

    public Map<Integer, String> findAllGames() {
        final String query = "select * from Game";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, String> games = new HashMap<>();
            while (resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                String state = resultSet.getString("state");
                games.put(gameId, state);
            }
            return games;
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 조회가 성공적으로 진행되지 않았습니다.");
        }
    }

    public String findStatusById(int boardId) {
        final String query = "select status from Game where game_id=?";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 보드 조회 중 오류가 발생했습니다.");
            }
            return resultSet.getString("status");
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 조회가 성공적으로 진행되지 않았습니다.");
        }
    }

    public void addGame(Side side) {
        final String query = "INSERT INTO Game(status) VALUES(?)";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, side.getName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 추가가 성공적으로 진행되지 않았습니다.");
        }
    }

    public void updateStatus(int gameId, String status) {
        final String query = "UPDATE Game SET status = ? WHERE id = ?";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 상태 업데이트가 성공적으로 진행되지 않았습니다.");
        }
    }
}
