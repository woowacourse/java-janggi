package janggi.dao;

import janggi.domain.GameInfo;
import janggi.db.SQLManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GameRoom {
    private final SQLManager sqlManager = new SQLManager("jdbc:sqlite:src/main/resources/game_room.db");

    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS GameRoom (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "created_date TEXT NOT NULL, " +
                "recently_date TEXT NOT NULL)";

        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GameInfo> findAllGames() {
        List<GameInfo> gameInfos = new ArrayList<>();
        String sql = "SELECT * FROM GameRoom";

        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GameInfo game = new GameInfo(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("created_date"),
                        rs.getString("recently_date")
                );
                gameInfos.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameInfos;
    }

    public void insertGame(String name, String createdDate, String recentlyDate) {
        String sql = "INSERT INTO GameRoom (name, created_date, recently_date) VALUES (?, ?, ?)";

        try (Connection conn = sqlManager.ensureConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, createdDate);
            pstmt.setString(3, recentlyDate);

            pstmt.executeUpdate();

            if (!conn.getAutoCommit()) conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeGame(int id) {
        String sql = "DELETE FROM Game WHERE id = ?";

        try (Connection conn = sqlManager.ensureConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                if (!conn.getAutoCommit()) conn.commit();
            } else {
                throw new IllegalStateException("해당 id의 게임이 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
