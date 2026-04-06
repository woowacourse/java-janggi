package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.util.EnumMap;
import java.util.Map;

public class ScoreBoard {

    private final Map<CampType, Double> scoreBoard;

    public ScoreBoard() {
        this.scoreBoard = new EnumMap<>(Map.of(
                CampType.CHO, CampType.CHO.getStartScore(),
                CampType.HAN, CampType.HAN.getStartScore()
        ));
    }

    public void minusScore(CampType campType, Piece piece) {
        scoreBoard.merge(campType, -piece.getScore(), Double::sum);
    }

    public Map<CampType, Double> getScoreBoard() {
        return Map.copyOf(scoreBoard);
    }
}
