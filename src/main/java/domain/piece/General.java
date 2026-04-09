package domain.piece;

import domain.game.Team;
import domain.position.Palace;
import domain.rule.PalaceDiagonalOneStepRule;
import domain.rule.PalaceOrthogonalRule;
import java.util.List;

public class General extends Piece {
    private static final double SCORE = 0.0;

    public General(Team team) {
        super(team, List.of(
                new PalaceOrthogonalRule(Palace.forTeam(team)),
                new PalaceDiagonalOneStepRule(Palace.forTeam(team))
        ));
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public double score() {
        return SCORE;
    }
}
