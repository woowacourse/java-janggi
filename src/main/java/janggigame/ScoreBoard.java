package janggigame;

import domain.piece.Piece;
import domain.piece.Side;
import domain.position.Position;

import java.util.EnumMap;
import java.util.Map;

public class ScoreBoard {
    private static final double HAN_INITIAL_SCORE = 1.5;

    private final Map<Side, Double> scores;

    private ScoreBoard(Map<Side, Double> scores) {
        this.scores = scores;
    }

    public static ScoreBoard from(Map<Position, Piece> state) {
        Map<Side, Double> scores = new EnumMap<>(Side.class);
        scores.put(Side.CHO, calculateScore(state, Side.CHO));
        scores.put(Side.HAN, calculateScore(state, Side.HAN) + HAN_INITIAL_SCORE);
        return new ScoreBoard(scores);
    }

    private static double calculateScore(Map<Position, Piece> state, Side side) {
        return state.values().stream()
                .filter(piece -> piece.isSameSide(side))
                .mapToDouble(Piece::getPieceValue)
                .sum();
    }

    public Side determineSide() {
        if (scores.get(Side.CHO) > scores.get(Side.HAN)) {
            return Side.CHO;
        }
        return Side.HAN;
    }

    public double getScoreBySide(Side side) {
        return scores.get(side);
    }
}
