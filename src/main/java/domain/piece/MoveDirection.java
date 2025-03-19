package domain.piece;

import domain.position.Position;

import java.util.List;

public enum MoveDirection {

    CROSS_INF,
    UP,
    DOWN,
    LEFT,
    RIGHT,

    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    ;

    public List<Position> calculateNextPositionsFrom(Position position) {
        return switch (this) {
            case UP -> List.of(position.withAddingRank());
            case DOWN -> List.of(position.withSubtractingRank());
            case RIGHT -> List.of(position.withAddingFile());
            case LEFT -> List.of(position.withSubtractingFile());
            case UP_RIGHT -> List.of(position.withAddingRank().withAddingFile());
            case UP_LEFT -> List.of(position.withAddingRank().withSubtractingFile());
            case DOWN_RIGHT -> List.of(position.withSubtractingRank().withAddingFile());
            case DOWN_LEFT -> List.of(position.withSubtractingRank().withSubtractingFile());
            case CROSS_INF -> position.getAllCrossPositions();
        };
    }
}
