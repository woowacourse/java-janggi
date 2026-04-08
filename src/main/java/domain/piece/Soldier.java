package domain.piece;

import domain.game.Team;
import domain.rule.ForwardAndSideRule;

public class Soldier extends Piece {
    private static final double SCORE = 2.0;

    public Soldier(Team team) {
        super(team, new ForwardAndSideRule(team.forwardRowDirection()));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
