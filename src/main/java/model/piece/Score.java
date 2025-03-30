package model.piece;

import java.util.Map;
import model.position.Position;

public class Score {

    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

    private final double redScore;
    private final double greenScore;

    private Score(int redScore, int greenScore) {
        this.redScore = redScore;
        this.greenScore = greenScore + SECOND_PLAYER_BONUS_SCORE;
    }

    public static Score calculateScoreFrom(Map<Position, Piece> pieces) {
        return calculateScore(pieces);
    }

    private static Score calculateScore(Map<Position, Piece> pieces) {
        int redScore = 0;
        int greenScore = 0;
        for (Position position : pieces.keySet()) {
            Piece piece = pieces.get(position);
            if (piece.getTeam() == Team.RED) {
                redScore += piece.getScore();
            }
            if (piece.getTeam() == Team.GREEN) {
                greenScore += piece.getScore();
            }
        }
        return new Score(redScore, greenScore);
    }

    public double getRedScore() {
        return redScore;
    }

    public double getGreenScore() {
        return greenScore;
    }
}
