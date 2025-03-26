package piece.player;

import java.util.Map;
import piece.Piece;
import piece.PieceScore;

public class PlayerScores {

    private int blueScore = 0;
    private int redScore = 0;

    public void add(Piece deadPiece) {
        Team team = deadPiece.team();
        PieceScore deadPieceScore = deadPiece.getPieceScore();
        if (team == Team.BLUE) {
            redScore += deadPieceScore.getPoint();
            return;
        }
        blueScore += deadPieceScore.getPoint();
    }

    public Map<Team, Integer> getCurrentPlayersScores() {
        return Map.of(Team.BLUE, blueScore, Team.RED, redScore);
    }
}
