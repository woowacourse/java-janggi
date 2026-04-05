package janggigame;

import domain.piece.Side;

import java.util.Map;

public class ScoreBoard {
    private final Map<Side, Double> scores;

    public ScoreBoard(Map<Side, Double> scores) {
        this.scores = scores;
    }

    public Map<Side, Double> getScores() {
        return Map.copyOf(scores);
    }

    public Side determineSide() {
        if (scores.get(Side.CHO) > scores.get(Side.HAN)) {
            return Side.CHO;
        }
        return Side.HAN;
    }
}
