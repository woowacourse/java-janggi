package janggi.domain.piece.behavior.palace;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.PieceBehavior;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PalaceBehavior implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(
            Movement.DOWN,
            Movement.LEFT,
            Movement.RIGHT,
            Movement.UP
    );

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(
            Movement.RIGHT_UP,
            Movement.RIGHT_DOWN,
            Movement.LEFT_UP,
            Movement.LEFT_DOWN
    );

    @Override
    public final Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        return Movement.getAvailableMovements(position, STANDARD_MOVEMENTS, CROSS_MOVEMENTS).stream()
                .map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .filter(Position::isPalace)
                .collect(Collectors.toUnmodifiableSet());
    }
}
