package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import java.util.List;

public class Soldier extends Piece {

    private static final int SOLDIER_SCORE = 2;

    private static final List<List<Direction>> RED_GUARD_MOVES = List.of(
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT)
    );

    private static final List<List<Direction>> BLUE_GUARD_MOVES = List.of(
            List.of(UP),
            List.of(LEFT),
            List.of(RIGHT)
    );

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return SOLDIER_SCORE;
    }

    protected List<List<Direction>> getMoveStrategy() {
        if (team == RED) {
            return RED_GUARD_MOVES;
        }
        return BLUE_GUARD_MOVES;
    }
}
