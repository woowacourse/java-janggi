package dao;

import database.ConnectionManager;
import domain.piece.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private final ConnectionManager manager;

    public BoardDao(ConnectionManager manager) {
        this.manager = manager;
    }

    public void saveScore(Map<Country, Integer> scoreByCountry) {
        String sql = "REPLACE INTO board_score (country, score) VALUES (?, ?)";

        try (PreparedStatement pstmt = manager.getConnection().prepareStatement(sql)) {
            for (Map.Entry<Country, Integer> entry : scoreByCountry.entrySet()) {
                pstmt.setString(1, entry.getKey().name());
                pstmt.setInt(2, entry.getValue());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException("점수를 저장하는 데에 오류가 생겼습니다.", e);
        }
    }

    public Map<Country, Integer> loadScore() {
        String sql = "SELECT * FROM board_score";
        Map<Country, Integer> scoreMap = new HashMap<>();

        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Country country = Country.valueOf(rs.getString("country"));
                int score = rs.getInt("score");
                scoreMap.put(country, score);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("점수를 불러오는 데에 오류가 생겼습니다.", e);
        }

        return scoreMap;
    }
}
