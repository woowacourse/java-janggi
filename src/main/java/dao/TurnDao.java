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
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, turn.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] 턴 정보를 저장하지 못했습니다.");
            e.printStackTrace();
        }
    }

    public void updateTurn(Connection connection, Team turn) {
        String sql = "UPDATE turn SET current_turn=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, turn.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] 턴 정보를 저장하지 못했습니다.");
            e.printStackTrace();
        }
    }

    public void removeAll() {
        String sql = "DELETE FROM turn";

        try (Connection connection = JdbcConnection.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] 해당 팀의 플레이어를 찾을 수 없습니다.");
            e.printStackTrace();
        }
    }

    public String getTurn() {
        String sql = "SELECT current_turn FROM turn";

        try (Connection connection = JdbcConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet turn = preparedStatement.executeQuery();
            while (turn.next()) {
                return turn.getString("current_turn");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] 턴 정보를 저장하지 못했습니다.");
            e.printStackTrace();
        }

        return null;
    }
}
