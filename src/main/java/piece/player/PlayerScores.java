package piece.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import piece.PieceScore;

public class PlayerScores {

    private final Map<Team, Integer> scores;

    public PlayerScores() {
        scores = new HashMap<>(Map.of(Team.BLUE, 0, Team.RED, 0));
    }

    public void addScore(Piece deadPiece) {
        Team deaePieceTeam = deadPiece.team();
        PieceScore deadPieceScore = deadPiece.pieceScore();
        scores.compute(deaePieceTeam.opposite(), (team, currentPoint) -> currentPoint + deadPieceScore.getPoint());
    }

    public Map<Team, Integer> getCurrentPlayersScores() {
        return Collections.unmodifiableMap(scores);
    }
}
