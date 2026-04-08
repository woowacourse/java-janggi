package domain.piece;

import domain.game.Team;
import domain.rule.ForwardAndSideRule;
import domain.rule.PalaceDiagonalForwardRule;
import java.util.List;

public class Soldier extends Piece {
    private static final double SCORE = 2.0;

    public Soldier(Team team) {
        super(team, List.of(
                new ForwardAndSideRule(team.forwardRowDirection()),
                new PalaceDiagonalForwardRule(team.forwardRowDirection())
        ));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
