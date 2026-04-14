package dto.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public record LoadedGameState(long gameId, double choScore, double hanScore, String turnTeam,
                              List<LoadedPiece> pieces) {
    public static LoadedGameState fromHeaderResultSet(long gameId, ResultSet headerRs, List<LoadedPiece> pieces)
            throws SQLException {
        if (!headerRs.next()) {
            throw new IllegalStateException("재개할 수 없는 게임입니다: id=" + gameId);
        }
        return new LoadedGameState(
                gameId,
                headerRs.getBigDecimal("cho_score").doubleValue(),
                headerRs.getBigDecimal("han_score").doubleValue(),
                headerRs.getString("turn_team"),
                pieces
        );
    }
}
