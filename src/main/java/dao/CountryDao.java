package dao;

import piece.Country;
import position.LineDirection;

import java.sql.*;
import java.util.EnumMap;
import java.util.Map;

import static db.ConnectionManager.getConnection;

public class CountryDao {

    public void saveDirection(Map<Country, LineDirection> directionMap) {
        String sql = "REPLACE INTO country_direction (country, direction) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Country, LineDirection> entry : directionMap.entrySet()) {
                pstmt.setString(1, entry.getKey().name());
                pstmt.setString(2, entry.getValue().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Country, LineDirection> loadDirections() {
        String sql = "SELECT * FROM country_direction";
        Map<Country, LineDirection> map = new EnumMap<>(Country.class);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = Country.valueOf(rs.getString("country"));
                LineDirection direction = LineDirection.valueOf(rs.getString("direction"));
                map.put(country, direction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }
}
