package domain.piece;

import domain.state.Side;
import domain.coordinate.Direction;
import domain.strategy.PathBasedMoveStrategy;

import java.util.List;

public final class Elephant extends Piece {

    private static final int SCORE = 3;

    private static final List<List<Direction>> ELEPHANT_PATHS = List.of(
            List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
            List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
            List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
            List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
            List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)
    );

    public Elephant(Side side) {
        super(
                PieceType.ELEPHANT,
                side,
                new PathBasedMoveStrategy(ELEPHANT_PATHS)
        );
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
