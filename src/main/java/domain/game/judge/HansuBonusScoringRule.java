package domain.game.judge;

import domain.board.Board;
import domain.game.Team;
import domain.piece.Piece;
import java.util.Map;

public final class HansuBonusScoringRule implements ScoringRule {
    private static final Map<Team, Double> BONUS = Map.of(
            Team.CHO, 0.0,
            Team.HAN, 1.5
    );

    @Override
    public double score(Board board, Team team) {
        double pieceSum = board.findPiecesByTeam(team).stream()
                .mapToDouble(Piece::score)
                .sum();
        return pieceSum + BONUS.getOrDefault(team, 0.0);
    }
}
