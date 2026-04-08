package domain.piece;

import domain.game.Team;
import domain.rule.LShapeRule;
import java.util.List;

public class Horse extends Piece {
    private static final double SCORE = 5.0;

    public Horse(Team team) {
        super(team, List.of(new LShapeRule()));
    }

    @Override
    public double score() {
        return SCORE;
    }
}
