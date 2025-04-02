package janggi.dao;

import janggi.domain.Turn;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static janggi.dao.DatabaseManager.executePreparedStatement;

public class GameDao {

    public Map<Integer, String> findAllGames() {
        final String query = "select * from Game";
        return executePreparedStatement(query, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Integer, String> games = new HashMap<>();
            while (resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                String state = resultSet.getString("state");
                games.put(gameId, state);
            }
            return games;
        });
    }

    public String findStateById(int gameId) {
        final String query = "select state from Game where game_id=?";
        return executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalStateException("[ERROR] 게임 조회 중 오류가 발생했습니다.");
            }
            return resultSet.getString("state");
        });
    }

    public void addGame(Turn side) {
        final String query = "INSERT INTO Game(state) VALUES(?)";
        executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setString(1, side.getName());
            return preparedStatement.executeUpdate();
        });
    }

    public void updateState(int gameId, Turn turn) {
        final String query = "UPDATE Game SET state = ? WHERE game_id = ?";
        executePreparedStatement(query, preparedStatement -> {
            preparedStatement.setString(1, turn.getName());
            preparedStatement.setInt(2, gameId);

            return preparedStatement.executeUpdate();
        });
    }
}
