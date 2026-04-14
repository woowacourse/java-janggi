package dto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record ResumableGame(long id, double choScore, double hanScore, Timestamp updatedAt) {
    public static ResumableGame fromRow(ResultSet rs) throws SQLException {
        return new ResumableGame(
                rs.getLong("id"),
                rs.getBigDecimal("cho_score").doubleValue(),
                rs.getBigDecimal("han_score").doubleValue(),
                rs.getTimestamp("updated_at")
        );
    }
}
