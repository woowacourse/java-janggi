package janggi.domain.piece;

import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.LEFT_DOWN;
import static janggi.domain.piece.direction.Direction.LEFT_UP;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.RIGHT_DOWN;
import static janggi.domain.piece.direction.Direction.RIGHT_UP;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import java.util.List;

public class Horse extends Piece {

    private static final int HORSE_SCORE = 5;

    private static final List<List<Direction>> HORSE_MOVES = List.of(
            List.of(RIGHT, RIGHT_UP),
            List.of(RIGHT, RIGHT_DOWN),

            List.of(LEFT, LEFT_UP),
            List.of(LEFT, LEFT_DOWN),

            List.of(UP, RIGHT_UP),
            List.of(UP, LEFT_UP),

            List.of(DOWN, RIGHT_DOWN),
            List.of(DOWN, LEFT_DOWN)
    );

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return HORSE_SCORE;
    }

    @Override
    protected List<List<Direction>> getMoveStrategy() {
        return HORSE_MOVES;
    }
}
