package domain.piece.move;

import domain.piece.move.area.MoveAreaConstraint;
import domain.position.Direction;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class LimitedMoveRule implements MoveRule {
    private final List<Direction> directions;
    private final int moveCount;
    private final MoveAreaConstraint moveAreaConstraint;

    public LimitedMoveRule(List<Direction> directions, int moveCount, MoveAreaConstraint moveAreaConstraint) {
        this.directions = directions;
        this.moveCount = moveCount;
        this.moveAreaConstraint = moveAreaConstraint;
    }

    @Override
    public List<Position> getIntermediatePath(Position from, Position to) {
        validateMoveArea(to);
        Direction reachableDirection = getReachableDirection(from, to);
        return getIntermediatePathByDirection(from, reachableDirection);
    }

    private void validateMoveArea(Position to) {
        if (!moveAreaConstraint.canMoveArea(to)) {
            throw new IllegalArgumentException("지정된 지역으로 이동할 수 없는 기물입니다.");
        }
    }

    private Direction getReachableDirection(Position from, Position to) {
        return directions.stream()
                .filter(direction -> isReachable(from, to, direction))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지정한 포지션으로 이동할 수 없습니다."));
    }

    private boolean isReachable(Position from, Position to, Direction direction) {
        Position movePosition = from;

        for (int i = 0; i < moveCount; i++) {
            if (!movePosition.canMoveDirection(direction) || !moveAreaConstraint.canMoveArea(movePosition)) {
                return false;
            }
            movePosition = movePosition.moveDirection(direction);
        }

        return movePosition.equals(to);
    }

    private List<Position> getIntermediatePathByDirection(Position from, Direction direction) {
        List<Position> intermediatePath = new ArrayList<>();
        Position movePosition = from;
        for (int i = 0; i < moveCount; i++) {
            movePosition = movePosition.moveDirection(direction);
            intermediatePath.add(movePosition);
        }
        intermediatePath.removeLast();
        return intermediatePath;
    }
}
