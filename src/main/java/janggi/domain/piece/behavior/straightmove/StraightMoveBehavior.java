package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.PieceBehavior;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class StraightMoveBehavior implements PieceBehavior {

    public static final Set<Movement> STANDARD_MOVEMENTS = Set.of(
            Movement.DOWN, Movement.LEFT, Movement.RIGHT, Movement.UP
    );

    public static final Set<Movement> CROSS_MOVEMENTS = Set.of(
            Movement.LEFT_DOWN, Movement.LEFT_UP, Movement.RIGHT_DOWN, Movement.RIGHT_UP
    );

    public final Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = getPositions(STANDARD_MOVEMENTS, position, board, side);
        if (position.canCrossMove()) {
            Set<Position> crossMovePositions = getPositions(CROSS_MOVEMENTS, position, board, side)
                    .stream()
                    .filter(Position::isPalace)
                    .collect(Collectors.toUnmodifiableSet());

            result.addAll(crossMovePositions);
        }

        return result;
    }

    private Set<Position> getPositions(Set<Movement> movements, Position position, Board board, Side side) {
        Set<Position> result = new HashSet<>();

        for (Vector vector : getVectors(movements)) {
            exploreSearchMove(position, board, side, vector, result);
        }
        return result;
    }

    private void exploreSearchMove(Position position, Board board, Side side, Vector vector, Set<Position> result) {
        if (position.canNotMove(vector)) {
            return;
        }

        Position movePosition = position.moveToNextPosition(vector);
        searchAvailableMoves(result, board, movePosition, vector, side);
    }

    private Set<Vector> getVectors(Set<Movement> movements) {
        return movements.stream()
                .map(Movement::getVector)
                .collect(Collectors.toUnmodifiableSet());
    }

    protected abstract void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition,
                                                 Vector vector,
                                                 Side side);
}
