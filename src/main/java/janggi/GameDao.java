package janggi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class GameDao {
    private static final String url = System.getenv("DB_URL");
    private static final String username = System.getenv("DB_USERNAME");
    private static final String password = System.getenv("DB_PASSWORD");

    public void create() {
        String sql = "insert into game(id, turn) values (1, 'GREEN');";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyGameExistsException("이미 진행중인 게임이 있습니다");
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public Team readTurn() {
        String sql = "select turn from game where id = 1;";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("turn"));
            }
            throw new IllegalArgumentException("게임 정보가 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void updateTurn(Team team) {
        String sql = "update game set turn = ? where id = 1;";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, team.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void delete() {
        String query = "delete from game where id = 1";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }
}
