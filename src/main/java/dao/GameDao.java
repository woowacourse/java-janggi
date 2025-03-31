package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Game;
import model.Team;
import util.DBConnectionManager;

public class GameDao {

    public Game selectById(int gameId) {
        String query = "SELECT id, turn FROM games WHERE id=?;";
        return DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, gameId);
                var resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int id = resultSet.getInt(1);
                Team turn = Team.from(resultSet.getString(2));
                Game game = new Game(turn);
                game.setId(id);
                return game;
            } catch (SQLException e) {
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
            }
        }, query);
    }

    public int insert(Game game) {
        String query = "INSERT INTO games (turn) VALUES (?);";
        return DBConnectionManager.useDBConnectionWithStatement(preparedStatement -> {
            try {
                preparedStatement.setString(1, game.getTurn().name());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
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
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
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
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
            }
        }, query);

    }
}
