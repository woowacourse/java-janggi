package janggigame;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.Side;

import java.util.EnumMap;
import java.util.Map;

public class ScoreBoard {
    private static final double HAN_INITIAL_SCORE = 1.5;

    private final Map<Side, Double> scores;

    public ScoreBoard(Map<Side, Double> scores) {
        this.scores = scores;
    }

    public static ScoreBoard from(Board board) {
        Map<Side, Double> scores = new EnumMap<>(Side.class);
        scores.put(Side.CHO, calculateScore(board, Side.CHO));
        scores.put(Side.HAN, calculateScore(board, Side.HAN) + HAN_INITIAL_SCORE);
        return new ScoreBoard(scores);
    }

    public static double calculateScore(Board board, Side side) {
        return board.getState().values().stream()
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
