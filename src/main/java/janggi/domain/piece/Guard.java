package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import java.util.List;

public class Guard extends Piece {

    private static final int GUARD_SCORE = 3;

    private static final List<List<Direction>> GUARD_MOVES = List.of(
            List.of(UP),
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT)
    );

    public Guard(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return GUARD_SCORE;
    }

    @Override
    protected List<List<Direction>> getMoveStrategy() {
        return GUARD_MOVES;
    }
}
