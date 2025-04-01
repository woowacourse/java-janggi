package janggi.domain.piece.behavior.straightmove;

import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceBehavior;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class StraightMoveBehavior implements PieceBehavior {

    private static final Set<Movement> STANDARD_MOVEMENTS = Set.of(
            Movement.DOWN, Movement.LEFT, Movement.RIGHT, Movement.UP
    );

    private static final Set<Movement> CROSS_MOVEMENTS = Set.of(
            Movement.LEFT_DOWN, Movement.LEFT_UP, Movement.RIGHT_DOWN, Movement.RIGHT_UP
    );

    @Override
    public final Set<Position> generateAvailableMovePositions(BoardPositionInfo boardPositionInfo) {
        Position position = boardPositionInfo.position();

        Set<Position> result = getPositions(STANDARD_MOVEMENTS, boardPositionInfo);
        if (position.canCrossMove()) {
            Set<Position> crossMovePositions = getPositions(CROSS_MOVEMENTS, boardPositionInfo)
                    .stream()
                    .filter(Position::isPalace)
                    .collect(Collectors.toUnmodifiableSet());

            result.addAll(crossMovePositions);
        }

        return result;
    }

    private Set<Position> getPositions(Set<Movement> movements, BoardPositionInfo boardPositionInfo) {
        Set<Position> result = new HashSet<>();

        for (Vector vector : getVectors(movements)) {
            searchMove(boardPositionInfo, vector, result);
        }
        return result;
    }

    private void searchMove(BoardPositionInfo boardPositionInfo, Vector vector, Set<Position> result) {
        Position position = boardPositionInfo.position();
        if (position.canNotMove(vector)) {
            return;
        }

        Position movePosition = position.moveToNextPosition(vector);
        searchAvailableMoves(result, boardPositionInfo.movePosition(movePosition), vector);
    }

    private Set<Vector> getVectors(Set<Movement> movements) {
        return movements.stream()
                .map(Movement::getVector)
                .collect(Collectors.toUnmodifiableSet());
    }

    protected abstract void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo,
                                                 Vector vector);
}
