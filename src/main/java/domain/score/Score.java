package domain.score;

import domain.piece.Piece;
import java.util.List;

public final class Score {
    private static final double HAN_ADVANTAGE = 1.5;

    private final double choScore;
    private final double hanScore;

    public Score(double choScore, double hanScore) {
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    public static Score from(List<Piece> pieces) {
        double choScore = pieces.stream()
                .filter(Piece::isCho)
                .mapToDouble(Piece::getPoint)
                .sum();
        double hanScore = pieces.stream()
                .filter(Piece::isHan)
                .mapToDouble(Piece::getPoint)
                .sum() + HAN_ADVANTAGE;
        return new Score(choScore, hanScore);
    }

    public double getChoScore() {
        return choScore;
    }

    public double getHanScore() {
        return hanScore;
    }
}
