package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.PieceBehavior;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Guard implements PieceBehavior {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.DOWN, Movement.UP, Movement.LEFT, Movement.RIGHT,
            Movement.LEFT_UP, Movement.LEFT_DOWN, Movement.RIGHT_UP, Movement.RIGHT_DOWN
    );
    
    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        return MOVEMENTS.stream()
                .map(Movement::getVector)
                .map(vector -> position.calculateNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String toName() {
        return "사";
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
