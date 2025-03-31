package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import team.Player;

public class PlayerDao {

    public int addPlayer(Player player) {
        String sql = "INSERT INTO player (score, team) VALUES (?, ?)";
        int autoIncrementId = -1;

        try (Connection connection = JdbcConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, player.getScore());
            preparedStatement.setString(2, player.getTeam().name());

            preparedStatement.executeUpdate();

            ResultSet keyResult = preparedStatement.getGeneratedKeys();
            if (keyResult.next()) {
                autoIncrementId = keyResult.getInt(1);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("[ERROR] 플레이어 정보를 저장하지 못했습니다.");
            e.printStackTrace();
        }

        return autoIncrementId;
    }

    public void updatePlayer(Connection connection, Player player) {
        String sql = "UPDATE player SET score=? WHERE team = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, player.getScore());
            preparedStatement.setString(2, player.getTeam().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] 플레이어 정보를 저장하지 못했습니다.");
            e.printStackTrace();
        }
    }

    public int countPlayer() {
        String query = "SELECT COUNT(*) FROM player";

        try (Connection connection = JdbcConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] 플레이어 목록을 읽어올 수 없습니다.");
            e.printStackTrace();
        }

        return 0;
    }

    public void removeAll() {
        String sql = "DELETE FROM player";

        try (Connection connection = JdbcConnection.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] 해당 팀의 플레이어를 찾을 수 없습니다.");
            e.printStackTrace();
        }
    }
}
