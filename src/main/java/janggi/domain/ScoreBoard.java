package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.util.EnumMap;
import java.util.Map;

public class ScoreBoard {

    private static final double CHO_INITIAL_SCORE = 0.0;
    private static final double HAN_INITIAL_SCORE = 1.5;

    private final Map<CampType, Double> scoreBoard;

    public static ScoreBoard create() {
        return new ScoreBoard(CampType.CHO.getStartScore(), CampType.HAN.getStartScore());
    }

    public static ScoreBoard restore(Map<Position, Piece> pieces) {
        Map<CampType, Double> scores = new EnumMap<>(Map.of(
                CampType.CHO, CHO_INITIAL_SCORE,
                CampType.HAN, HAN_INITIAL_SCORE
        ));
        pieces.values().forEach(piece ->
                scores.merge(piece.campType(), piece.getScore(), Double::sum)
        );
        return new ScoreBoard(scores.get(CampType.CHO), scores.get(CampType.HAN));
    }

    private ScoreBoard(double choScore, double hanScore) {
        this.scoreBoard = new EnumMap<>(Map.of(
                CampType.CHO, choScore,
                CampType.HAN, hanScore
        ));
    }

    public void minusScore(CampType campType, Piece piece) {
        scoreBoard.merge(campType, -piece.getScore(), Double::sum);
    }

    public Map<CampType, Double> getScoreBoard() {
        return Map.copyOf(scoreBoard);
    }
}
