package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.util.EnumMap;
import java.util.Map;

public class ScoreBoard {

    private final Map<CampType, Double> scoreBoard;

    public static ScoreBoard create() {
        return new ScoreBoard(CampType.CHO.getStartScore(), CampType.HAN.getStartScore());
    }

    public static ScoreBoard restore(Map<Position, Piece> pieces) {
        double choScore = 0;
        double hanScore = 1.5;
        for (Piece piece : pieces.values()) {
            if (piece.campType() == CampType.CHO) {
                choScore += piece.getScore();
                continue;
            }
            hanScore += piece.getScore();
        }
        return new ScoreBoard(choScore, hanScore);
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
