package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import team.Team;

public class TurnDao {

    public void addTurn(Team turn) {
        String sql = "INSERT INTO turn (current_turn) VALUES (?)";

        try (Connection connection = JdbcConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, turn.name());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 정보를 저장하지 못했습니다.");
        }
    }

    public void updateTurn(Team turn) {
        String sql = "UPDATE turn SET current_turn=?";

        try (Connection connection = JdbcConnection.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, turn.name());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 정보를 업데이트하지 못했습니다.");
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM turn";

        try (Connection connection = JdbcConnection.getConnection()){
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 정보를 삭제하지 못했습니다.");
        }
    }

    public String getTurn() {
        String sql = "SELECT current_turn FROM turn";

        try (Connection connection = JdbcConnection.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet turnResult = preparedStatement.executeQuery()) {
                    if (turnResult.next()) {
                        return turnResult.getString("current_turn");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 턴 정보를 조회하는데 문제가 발생했습니다.");
        }

        throw new IllegalStateException("[ERROR] 턴 정보를 조회하지 못했습니다.");
    }
}
