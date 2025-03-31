package dao;

import piece.Country;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private final PieceDao pieceDao = new PieceDao(); // 커넥션 재활용

    public void saveScore(Map<Country, Integer> scoreByCountry) {
        String sql = "REPLACE INTO board_score (country, score) VALUES (?, ?)";

        try (Connection conn = pieceDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Country, Integer> entry : scoreByCountry.entrySet()) {
                pstmt.setString(1, entry.getKey().name());
                pstmt.setInt(2, entry.getValue());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Country, Integer> loadScore() {
        String sql = "SELECT * FROM board_score";
        Map<Country, Integer> scoreMap = new HashMap<>();

        try (Connection conn = pieceDao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = Country.valueOf(rs.getString("country"));
                int score = rs.getInt("score");
                scoreMap.put(country, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreMap;
    }
}
