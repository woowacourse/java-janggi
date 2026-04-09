package domain.game;

import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class ScoreCalculator {
    private static final Map<Team, Double> BONUS_SCORES = Map.of(
            Team.CHO, 0.0,
            Team.HAN, 1.5
    );

    public double calculate(List<Piece> pieces, Team team) {
        double pieceScore = pieces.stream()
                .mapToDouble(Piece::score)
                .sum();
        return pieceScore + BONUS_SCORES.getOrDefault(team, 0.0);
    }
}
