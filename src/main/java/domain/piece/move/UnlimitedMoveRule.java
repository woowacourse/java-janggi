package domain.piece.move;

import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class UnlimitedMoveRule implements MoveRule {

    private final List<Direction> directions;

    public UnlimitedMoveRule(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Position> getIntermediatePath(Position from, Position to) {
        Direction reachableDirection = getReachableDirection(from, to);
        return getIntermediatePathByDirection(from, to, reachableDirection);
    }

    private Direction getReachableDirection(Position from, Position to) {
        return directions.stream()
                .filter(direction -> isReachable(from, to, direction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."));
    }

    private boolean isReachable(Position from, Position to, Direction direction) {
        Position movePosition = from;

        while (movePosition.canMoveDirection(direction)) {
            movePosition = movePosition.moveDirection(direction);
            if (movePosition.equals(to)) {
                return true;
            }
        }

        return false;
    }

    private List<Position> getIntermediatePathByDirection(Position from, Position to, Direction direction) {
        List<Position> intermediatePath = new ArrayList<>();
        Position movePosition = from;
        while (!movePosition.equals(to)) {
            movePosition = movePosition.moveDirection(direction);
            intermediatePath.add(movePosition);
        }
        intermediatePath.removeLast();
        return intermediatePath;
    }
}
