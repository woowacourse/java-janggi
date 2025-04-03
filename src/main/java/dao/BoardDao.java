package dao;

import piece.Country;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static db.ConnectionManager.getConnection;

public class BoardDao {

    public void saveScore(Map<Country, Integer> scoreByCountry) {
        String sql = "REPLACE INTO board_score (country, score) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Country, Integer> entry : scoreByCountry.entrySet()) {
                pstmt.setString(1, entry.getKey().name());
                pstmt.setInt(2, entry.getValue());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException("점수를 저장하는 데에 오류가 생겼습니다.");
        }
    }

    public Map<Country, Integer> loadScore() {
        String sql = "SELECT * FROM board_score";
        Map<Country, Integer> scoreMap = new HashMap<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = Country.valueOf(rs.getString("country"));
                int score = rs.getInt("score");
                scoreMap.put(country, score);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("점수를 불러오는 데에 오류가 생겼습니다.");
        }

        return scoreMap;
    }
}
