package domain.piece.policy;

import static domain.piece.strategy.Direction.LEFT_DOWN;
import static domain.piece.strategy.Direction.LEFT_UP;
import static domain.piece.strategy.Direction.RIGHT_DOWN;
import static domain.piece.strategy.Direction.RIGHT_UP;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.piece.strategy.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class GungseongDiagonalMovementPolicy implements MovementPolicy {
    private static final Map<Position, List<Direction>> gungseongMapper = Map.of(
            Position.of(1, 4), List.of(RIGHT_DOWN),
            Position.of(1, 6), List.of(LEFT_DOWN),
            Position.of(3, 4), List.of(RIGHT_UP),
            Position.of(3, 6), List.of(LEFT_UP),
            Position.of(2, 5), List.of(RIGHT_DOWN, RIGHT_UP, LEFT_UP, LEFT_DOWN),
            Position.of(8, 4), List.of(RIGHT_DOWN),
            Position.of(8, 6), List.of(LEFT_DOWN),
            Position.of(10, 4), List.of(RIGHT_UP),
            Position.of(10, 6), List.of(LEFT_UP),
            Position.of(9, 5), List.of(RIGHT_DOWN, RIGHT_UP, LEFT_UP, LEFT_DOWN));

    @Override
    public void validate(Board board, List<Position> path, Position start, Position destination) {
        Direction direction = Direction.getDirectionByPosition(start, path.getFirst());
        if (!direction.isDiagonal()) {
            return;
        }
        List<Direction> possibleDirections = findDirectionsByPosition(start);
        validateSameDirection(direction, possibleDirections);
        new GungseongBoundaryMovementPolicy().validate(board, path, start, destination);
    }

    private List<Direction> findDirectionsByPosition(Position start) {
        List<Direction> possibleDirections = gungseongMapper.get(start);
        if (possibleDirections == null) {
            throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
        }
        return possibleDirections;
    }

    private void validateSameDirection(Direction firstStep, List<Direction> possibleDirections) {
        for (Direction possibleDirection : possibleDirections) {
            if (firstStep == possibleDirection) {
                return;
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

}
