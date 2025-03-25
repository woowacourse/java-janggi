package janggi.domain.piece.behavior;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.piece.PieceBehavior;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Soldier implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT);

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(Movement.LEFT_DOWN, Movement.RIGHT_DOWN);

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> positions = getAvailableStandardMovePositions(board, position, side);

        if (position.canCrossMove()) {
            Set<Position> crossMovePositions = getAvailableCrossMovePositions(board, position, side);
            positions.addAll(crossMovePositions);
        }

        return positions;
    }

    @Override
    public String toName() {
        return "병";
    }

    private Set<Position> getAvailableStandardMovePositions(Board board, Position position, Side side) {
        return STANDARD_MOVEMENTS.stream()
                .map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .collect(Collectors.toSet());
    }

    private Set<Position> getAvailableCrossMovePositions(Board board, Position position, Side side) {
        return CROSS_MOVEMENTS.stream().map(Movement::getVector)
                .map(vector -> position.getValidNextPosition(vector.side(side)))
                .flatMap(Optional::stream)
                .filter(availablePosition -> board.canMoveToPosition(side, availablePosition))
                .filter(Position::isPalace)
                .collect(Collectors.toSet());
    }
}
