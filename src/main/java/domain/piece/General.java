package domain.piece;

import domain.game.Team;
import domain.rule.OrthogonalOneStepRule;

public class General extends Piece {
    private static final double SCORE = 0.0;

    public General(Team team) {
        super(team, new OrthogonalOneStepRule());
    }

    @Override
    public double score() {
        return SCORE;
    }
}
