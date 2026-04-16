package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class GameEntity {
    private final long gameId;
    private final double choScore;
    private final double hanScore;
    private final String turnTeam;
    private final List<PieceEntity> pieces;

    private GameEntity(long gameId, double choScore, double hanScore, String turnTeam, List<PieceEntity> pieces) {
        this.gameId = gameId;
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.turnTeam = turnTeam;
        this.pieces = pieces;
    }

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

    public long gameId() {
        return gameId;
    }

    public double choScore() {
        return choScore;
    }

    public double hanScore() {
        return hanScore;
    }

    public String turnTeam() {
        return turnTeam;
    }

    public List<PieceEntity> pieces() {
        return pieces;
    }
}
