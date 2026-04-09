package domain.game;

import domain.piece.Piece;
import java.util.List;

public class ScoreCalculator {

    public double calculate(List<Piece> pieces) {
        return pieces.stream()
                .mapToDouble(Piece::score)
                .sum();
    }
}
