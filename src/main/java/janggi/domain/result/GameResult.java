package janggi.domain.result;

import janggi.domain.Side;
import janggi.domain.piece.AlivePieces;
import java.util.EnumMap;
import java.util.Map;

public class GameResult {

    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

    private final Side winner;
    private final Map<Side, Double> scores;

    private GameResult(Side winner, Map<Side, Double> scores) {
        this.winner = winner;
        this.scores = Map.copyOf(scores);
    }

    public static GameResult calculate(AlivePieces pieces) {
        Side winner = determineWinner(pieces);

        Map<Side, Double> scores = new EnumMap<>(Side.class);

        double choScore = pieces.calculateScoreSum(Side.CHO);
        double hanScore = pieces.calculateScoreSum(Side.HAN) + SECOND_PLAYER_BONUS_SCORE;

        scores.put(Side.CHO, choScore);
        scores.put(Side.HAN, hanScore);

        return new GameResult(winner, scores);
    }

    private static Side determineWinner(AlivePieces alivePieces) {
        boolean isHanGungDead = alivePieces.isGungDead(Side.HAN);
        boolean isChoGungDead = alivePieces.isGungDead(Side.CHO);

        if (isHanGungDead) {
            return Side.CHO;
        }
        if (isChoGungDead) {
            return Side.HAN;
        }
        return Side.NONE;
    }

    public Side getWinner() {
        return winner;
    }

    public double getScoreOf(Side side) {
        return scores.getOrDefault(side, 0.0);
    }
}
