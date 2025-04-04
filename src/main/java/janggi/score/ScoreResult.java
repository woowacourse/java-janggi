package janggi.score;

import janggi.game.Team;
import janggi.movement.target.AttackedPiece;
import janggi.piece.PieceInformation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreResult {

    private static final double ADVANTAGE_SCORE = 1.5;

    private final Map<Team, Score> result;

    public ScoreResult(Map<Team, Score> result) {
        this.result = result;
    }

    public static ScoreResult initialize() {
        Map<Team, Score> result = new HashMap<>();
        result.put(Team.CHO, PieceInformation.calculateTotalScore());
        result.put(Team.HAN, PieceInformation.calculateTotalScore().plus(ADVANTAGE_SCORE));
        return new ScoreResult(result);
    }

    public void aggregate(List<AttackedPiece> attackedPieces) {
        for (AttackedPiece attackedPiece : attackedPieces) {
            if (attackedPiece.exists()) {
                Team attackedTeam = attackedPiece.getTeam();
                result.replace(attackedTeam, result.get(attackedTeam).minus(attackedPiece.getScore()));
            }
        }
    }

    public Map<Team, Score> getResult() {
        return Collections.unmodifiableMap(result);
    }
}
