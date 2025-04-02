package domain.piece.path;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class FixedMultiStepPathFinder implements PathFinder {
    private final List<Movement> movements;

    public FixedMultiStepPathFinder(List<Movement> movements) {
        this.movements = movements;
    }

    @Override
    public List<Position> findIntermediatePositions(Position from, Position to) {
        List<Direction> finMovement = movements.stream()
                .map(Movement::getMovement)
                .filter(movement -> isValidMove(from, to, movement))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다."));
        return getPathPositionsFrom(from, finMovement);
    }

    private boolean isValidMove(Position startPosition, Position endPosition, List<Direction> movement) {
        Position curPosition = startPosition;
        for (Direction direction : movement) {
            if (isInvalidMove(curPosition, direction)) {
                return false;
            }
            curPosition = curPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
        }
        return curPosition.equals(endPosition);
    }

    private List<Position> getPathPositionsFrom(Position startPosition, List<Direction> movement) {
        List<Position> pathPositions = new ArrayList<>();

        for (Direction direction : movement) {
            startPosition = startPosition.movePosition(direction.getDeltaRow(), direction.getDeltaColumn());
            pathPositions.add(startPosition);
        }

        pathPositions.removeLast();
        return pathPositions;
    }

    private boolean isInvalidMove(Position curPosition, Direction direction) {
        return !curPosition.canMovePosition(direction.getDeltaRow(), direction.getDeltaColumn());
    }
}
