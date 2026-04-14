package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public record GameEntity(long gameId, double choScore, double hanScore, String turnTeam,
                         List<PieceEntity> pieces) {
    public static GameEntity fromHeaderResultSet(long gameId, ResultSet headerRs, List<PieceEntity> pieces)
            throws SQLException {
        if (!headerRs.next()) {
            throw new IllegalStateException("재개할 수 없는 게임입니다: id=" + gameId);
        }
        return new GameEntity(
                gameId,
                headerRs.getBigDecimal("cho_score").doubleValue(),
                headerRs.getBigDecimal("han_score").doubleValue(),
                headerRs.getString("turn_team"),
                pieces
        );
    }
}
