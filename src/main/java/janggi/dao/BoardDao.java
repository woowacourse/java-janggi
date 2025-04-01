package janggi.dao;

import janggi.camp.Camp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void initializeBoard(Camp firstTurnCamp) {
        String query = "INSERT INTO board (is_end, turn) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, firstTurnCamp.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("보드 초기화 실패", e);
        }
    }

    public Camp findLatestTurn() {
        String query = "SELECT * FROM board WHERE id = ?";
        int boardId = findActiveBoardId();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.from(resultSet.getString("turn"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException("현재 턴 조회 실패", e);
        }
        return null;
    }

    public int findActiveBoardId() {
        String query = "SELECT * FROM board WHERE is_end = false";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException("진행 중인 보드 조회 실패", e);
        }
        return 0;
    }

    public void endBoard() {
        String query = "UPDATE board SET is_end = ? WHERE id= ?";
        int boardId = findActiveBoardId();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, boardId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("게임 종료 처리 실패", e);
        }
    }

    public void updateTurn(Camp camp) {
        String query = "UPDATE board SET turn = ? WHERE id= ?";
        int boardId = findActiveBoardId();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            preparedStatement.setInt(2, boardId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("진행 중인 보드의 턴 업데이트 실패", e);
        }
    }

    public boolean isNewGame() {
        String query = "SELECT EXISTS (SELECT 1 FROM board WHERE is_end = false)";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return !resultSet.getBoolean(1);
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("새 게임 확인 실패", e);
        }
    }
}
