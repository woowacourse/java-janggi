package dao;

import database.ConnectionManager;
import domain.piece.Country;
import domain.position.LineDirection;

import java.sql.*;
import java.util.EnumMap;
import java.util.Map;

public class CountryDirectionDao {

    private final ConnectionManager manager;

    public CountryDirectionDao(ConnectionManager manager) {
        this.manager = manager;
    }

    public void saveDirection(Map<Country, LineDirection> directionMap) {
        String sql = "REPLACE INTO country_direction (country, direction) VALUES (?, ?)";

        try (PreparedStatement pstmt = manager.getConnection().prepareCall(sql)) {

            for (Map.Entry<Country, LineDirection> entry : directionMap.entrySet()) {
                pstmt.setString(1, entry.getKey().name());
                pstmt.setString(2, entry.getValue().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException("나라를 저장하는 데에 오류가 생겼습니다.");
        }
    }

    public Map<Country, LineDirection> loadDirections() {
        String sql = "SELECT * FROM country_direction";
        Map<Country, LineDirection> map = new EnumMap<>(Country.class);

        try (PreparedStatement stmt = manager.getConnection().prepareStatement(sql)) {
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Country country = Country.valueOf(rs.getString("country"));
                LineDirection direction = LineDirection.valueOf(rs.getString("direction"));
                map.put(country, direction);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("나라를 불러오는 데에 오류가 생겼습니다.");
        }

        return map;
    }
}
