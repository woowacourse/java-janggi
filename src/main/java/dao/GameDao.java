package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import model.Team;
import util.DBConnectionManager;

public class GameDao {

    public static final String QUERY_EXECUTION_ERROR_MESSAGE = "DB 쿼리 실행 중 오류가 발생했습니다.";

    public List<Game> selectAll() {
        String query = "SELECT id, name, turn FROM games;";
        return DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                List<Game> games = new ArrayList<>();
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    Team turn = Team.from(resultSet.getString(3));
                    Game game = new Game(name, turn);
                    game.setId(id);
                    games.add(game);
                }
                return games;
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public Game selectById(int gameId) {
        String query = "SELECT id, name, turn FROM games WHERE id=?;";
        return DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, gameId);
                var resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Team turn = Team.from(resultSet.getString(3));
                Game game = new Game(name, turn);
                game.setId(id);
                return game;
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public int insert(Game game) {
        String query = "INSERT INTO games (name, turn) VALUES (?, ?);";
        return DBConnectionManager.useDBConnectionWithStatement(preparedStatement -> {
            try {
                preparedStatement.setString(1, game.getName());
                preparedStatement.setString(2, game.getTurn().name());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query, Statement.RETURN_GENERATED_KEYS);
    }

    public void update(Game game) {
        String query = "UPDATE games SET turn=? WHERE id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setString(1, game.getTurn().name());
                preparedStatement.setInt(2, game.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public void delete(Game game) {
        String query = "DELETE FROM games WHERE id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, game.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }
}
