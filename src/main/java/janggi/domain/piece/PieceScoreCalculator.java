package janggi.domain.piece;

import janggi.domain.game.Side;
import java.util.Collection;

public class PieceScoreCalculator {

    private static final double CHO_ADVANTAGE = 1.5;

    public static double calculateScore(Side side, Collection<Piece> pieces) {
        double totalScore = 0;
        if (side.hasAdvantage()) {
            totalScore += CHO_ADVANTAGE;
        }
        totalScore += calculateOwnPieceScore(side, pieces);
        return totalScore;
    }

    public static double calculateOwnPieceScore(Side side, Collection<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece.isBelongTo(side))
                .mapToInt(Piece::getScore)
                .sum();
    }
}
