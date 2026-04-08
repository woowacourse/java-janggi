package domain.piece;

import domain.game.Team;
import domain.rule.OrthogonalOneStepRule;

public class Guard extends Piece {
    private static final double SCORE = 3.0;

    public Guard(Team team) {
        super(team, new OrthogonalOneStepRule());
    }

    @Override
    public double score() {
        return SCORE;
    }
}
