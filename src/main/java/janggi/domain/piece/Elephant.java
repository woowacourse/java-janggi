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

public class Elephant extends Piece {

    private static final int ELEPHANT_SCORE = 3;

    private static final List<List<Direction>> ELEPHANT_MOVES = List.of(
            List.of(UP, RIGHT_UP, RIGHT_UP),
            List.of(UP, LEFT_UP, LEFT_UP),

            List.of(RIGHT, RIGHT_UP, RIGHT_UP),
            List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),

            List.of(DOWN, LEFT_DOWN, LEFT_DOWN),
            List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),

            List.of(LEFT, LEFT_UP, LEFT_UP),
            List.of(LEFT, LEFT_DOWN, LEFT_DOWN)
    );

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return ELEPHANT_SCORE;
    }

    @Override
    protected List<List<Direction>> getMoveStrategy() {
        return ELEPHANT_MOVES;
    }
}
