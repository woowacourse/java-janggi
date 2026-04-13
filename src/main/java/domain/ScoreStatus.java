package domain;

import domain.piece.Piece;

import java.util.Collection;

public class ScoreStatus {

    private static final double HAN_BONUS = 1.5;

    private final double choScore;
    private final double hanScore;

    private ScoreStatus(double choScore, double hanScore) {
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    public static ScoreStatus from(Collection<Piece> pieces) {
        double cho = calculateScore(pieces, Side.CHO);
        double han = HAN_BONUS + calculateScore(pieces, Side.HAN);
        return new ScoreStatus(cho, han);
    }

    private static double calculateScore(Collection<Piece> pieces, Side side) {
        double score = 0;
        for (Piece piece : pieces) {
            score += getScoreIfSameSide(piece, side);
        }
        return score;
    }

    private static double getScoreIfSameSide(Piece piece, Side side) {
        if (piece.isSameSide(side)) {
            return piece.getScore();
        }
        return 0;
    }

    public Side winner() {
        if (choScore > hanScore) {
            return Side.CHO;
        }
        return Side.HAN;
    }

    public String choScoreText() {
        return formatScore(choScore);
    }

    public String hanScoreText() {
        return formatScore(hanScore);
    }

    private String formatScore(double score) {
        if (score == (int) score) {
            return String.valueOf((int) score);
        }
        return String.valueOf(score);
    }
}
