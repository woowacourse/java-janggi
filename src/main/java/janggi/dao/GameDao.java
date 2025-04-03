package janggi.dao;

import janggi.AlreadyGameExistsException;
import janggi.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class GameDao {
    private static final String url = "jdbc:mysql://localhost:13306/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "root";

    public void create(int id) {
        String sql = "insert into game(id, turn) values (?, 'GREEN');";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new AlreadyGameExistsException("이미 진행중인 게임이 있습니다");
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public Team readTurn(int id) {
        String sql = "select turn from game where id = ?;";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Team.valueOf(resultSet.getString("turn"));
                }
            }
            throw new IllegalArgumentException("게임 정보가 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void updateTurn(int id, Team team) {
        String sql = "update game set turn = ? where id = ?;";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    public void delete(int id) {
        String query = "delete from game where id = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 오류 발생");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
