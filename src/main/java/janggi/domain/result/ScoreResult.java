package janggi.domain.result;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ScoreResult {

    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

    private final Map<Side, Double> scores;

    private ScoreResult(Map<Side, Double> scores) {
        this.scores = Map.copyOf(scores);
    }

    public static ScoreResult calculate(List<Piece> pieces) {
        Map<Side, Double> scores = new EnumMap<>(Side.class);

        double choScore = calculateBaseScore(pieces, Side.CHO);
        double hanScore = calculateBaseScore(pieces, Side.HAN) + SECOND_PLAYER_BONUS_SCORE;

        scores.put(Side.CHO, choScore);
        scores.put(Side.HAN, hanScore);

        return new ScoreResult(scores);
    }

    private static double calculateBaseScore(List<Piece> pieces, Side side) {
        double totalScore = 0;
        totalScore += addScoreOfSameSide(pieces, side);
        return totalScore;
    }

    private static int addScoreOfSameSide(List<Piece> pieces, Side side) {
        return pieces.stream()
                .filter(piece -> piece.isSameSide(side))
                .map(Piece::getPieceType)
                .mapToInt(PieceType::getScore)
                .sum();
    }

    public double getScoreOf(Side side) {
        return scores.getOrDefault(side, 0.0);
    }
}
