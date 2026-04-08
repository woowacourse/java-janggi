package domain.piece;

import domain.game.Team;
import domain.rule.ExtendedLShapeRule;

public class Elephant extends Piece {
    private static final double SCORE = 3.0;

    public Elephant(Team team) {
        super(team, new ExtendedLShapeRule());
    }

    @Override
    public double score() {
        return SCORE;
    }
}
