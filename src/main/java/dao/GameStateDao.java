package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import player.Nation;

public class GameStateDao {

    public static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    public static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    public static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USERNAME = "root"; //  MySQL 서버 아이디
    public static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void insertGameState() {
        final var query = "INSERT INTO game_state (current_turn, status) VALUES (?, ?)";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "CHO");
            preparedStatement.setString(2, "ONGOING");
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeAttackNation(Nation defenseNation) {
        var query = "UPDATE game_state SET current_turn = ?";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, defenseNation.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Nation getCurrentTurn() {
        String query = "SELECT current_turn FROM game_state";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return Nation.getNationBy(resultSet.getString("current_turn"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 조회 중 오류가 발생하였습니다.", e);
        }
        throw new IllegalArgumentException("[ERROR] 조회 중 오류가 발생하였습니다.");
    }

    public void deleteAllGameState() {
        var query = "TRUNCATE TABLE game_state";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
