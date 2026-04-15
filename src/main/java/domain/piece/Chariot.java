package domain.piece;

import domain.game.Team;
import domain.rule.PalaceDiagonalStraightRule;
import domain.rule.StraightLineRule;
import java.util.List;

public class Chariot extends Piece {
    private static final double SCORE = 13.0;

    public Chariot(Team team) {
        super(team, List.of(
                new StraightLineRule(),
                new PalaceDiagonalStraightRule()
        ));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
