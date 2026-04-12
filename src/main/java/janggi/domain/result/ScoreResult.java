package janggi.domain.result;

import janggi.domain.Side;
import janggi.domain.piece.AlivePieces;
import java.util.EnumMap;
import java.util.Map;

public class ScoreResult {

    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

    private final Map<Side, Double> scores;

    private ScoreResult(Map<Side, Double> scores) {
        this.scores = Map.copyOf(scores);
    }

    public static ScoreResult calculate(AlivePieces pieces) {
        Map<Side, Double> scores = new EnumMap<>(Side.class);

        double choScore = pieces.calculateScoreSum(Side.CHO);
        double hanScore = pieces.calculateScoreSum(Side.HAN) + SECOND_PLAYER_BONUS_SCORE;

        scores.put(Side.CHO, choScore);
        scores.put(Side.HAN, hanScore);

        return new ScoreResult(scores);
    }

    public double getScoreOf(Side side) {
        return scores.getOrDefault(side, 0.0);
    }
}
