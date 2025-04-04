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
        int redScore = calculateScore(pieces, Team.RED);
        int greenScore = calculateScore(pieces, Team.GREEN);
        return calculateScore(redScore, greenScore);
    }

    private static int calculateScore(Map<Position, Piece> pieces, Team team) {
        return pieces.values().stream()
            .filter(piece -> piece.getTeam() == team)
            .mapToInt(Piece::getScore)
            .sum();
    }

    private static Score calculateScore(int redScore, int greenScore) {
        return new Score(redScore, greenScore);
    }

    public double getRedScore() {
        return redScore;
    }

    public double getGreenScore() {
        return greenScore;
    }
}
