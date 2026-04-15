package domain.piece;

import domain.game.Team;
import domain.rule.ExtendedLShapeRule;
import java.util.List;

public class Elephant extends Piece {
    private static final double SCORE = 3.0;

    public Elephant(Team team) {
        super(team, List.of(new ExtendedLShapeRule()));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
