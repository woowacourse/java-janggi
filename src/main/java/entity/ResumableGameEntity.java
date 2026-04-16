package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public final class ResumableGameEntity {
    private final long id;
    private final double choScore;
    private final double hanScore;
    private final Timestamp updatedAt;

    private ResumableGameEntity(long id, double choScore, double hanScore, Timestamp updatedAt) {
        this.id = id;
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.updatedAt = updatedAt;
    }

    public static ResumableGameEntity fromRow(ResultSet rs) throws SQLException {
        return new ResumableGameEntity(
                rs.getLong("id"),
                rs.getBigDecimal("cho_score").doubleValue(),
                rs.getBigDecimal("han_score").doubleValue(),
                rs.getTimestamp("updated_at")
        );
    }

    public long id() {
        return id;
    }

    public double choScore() {
        return choScore;
    }

    public double hanScore() {
        return hanScore;
    }

    public Timestamp updatedAt() {
        return updatedAt;
    }
}
