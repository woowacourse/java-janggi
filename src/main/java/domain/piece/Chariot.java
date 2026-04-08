package domain.piece;

import domain.game.Team;
import domain.rule.StraightLineRule;

public class Chariot extends Piece {
    private static final double SCORE = 13.0;

    public Chariot(Team team) {
        super(team, new StraightLineRule());
    }

    @Override
    public double score() {
        return SCORE;
    }
}
