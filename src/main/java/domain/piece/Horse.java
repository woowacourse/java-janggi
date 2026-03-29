package domain.piece;

import domain.board.Side;
import domain.coordinate.Direction;
import domain.rule.BoardBoundaryRule;
import domain.rule.BasicCaptureRule;
import domain.rule.MiddlePathBlockRule;
import domain.strategy.PathBasedMoveStrategy;

import java.util.List;

public final class Horse extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(Direction.UP, Direction.UP_LEFT),
            List.of(Direction.UP, Direction.UP_RIGHT),
            List.of(Direction.DOWN, Direction.DOWN_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT),
            List.of(Direction.LEFT, Direction.UP_LEFT),
            List.of(Direction.LEFT, Direction.DOWN_LEFT),
            List.of(Direction.RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT)
    );

    public Horse(Side side) {
        super(
                side,
                new PathBasedMoveStrategy(PATHS),
                List.of(
                        new BoardBoundaryRule(),
                        new BasicCaptureRule(),
                        new MiddlePathBlockRule(PATHS)
                )
        );
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
