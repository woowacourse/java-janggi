package domain;

import domain.coordinate.Position;
import java.util.Map;

public class ScoreCalculator {

    private static final double HAN_BONUS_SCORE = 1.5;

    public double calculate(Map<Position, CellSnapshot> boardPieces, Side side) {
        int allPiecesScore = boardPieces.values().stream()
                .filter(cell -> cell.side() == side)
                .mapToInt(cell -> cell.type().getScore())
                .sum();
        if (side.isHan()) {
            return allPiecesScore + HAN_BONUS_SCORE;
        }
        return allPiecesScore;
    }
}