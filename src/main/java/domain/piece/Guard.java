package domain.piece;

import domain.game.Team;
import domain.position.Palace;
import domain.rule.PalaceDiagonalOneStepRule;
import domain.rule.PalaceOrthogonalRule;
import java.util.List;

public class Guard extends Piece {
    private static final double SCORE = 3.0;

    public Guard(Team team) {
        super(team, List.of(
                new PalaceOrthogonalRule(Palace.forTeam(team)),
                new PalaceDiagonalOneStepRule(Palace.forTeam(team))
        ));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
