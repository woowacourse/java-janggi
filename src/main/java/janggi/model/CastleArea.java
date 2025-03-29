package janggi.model;

import java.util.Arrays;
import java.util.List;

public enum CastleArea {
    BLUE_LEFT_TOP(new Position(8, 4), List.of(Direction.RIGHT_BOTTOM, Direction.RIGHT, Direction.BOTTOM)),
    BLUE_RIGHT_TOP(new Position(8, 6), List.of(Direction.LEFT_BOTTOM, Direction.LEFT, Direction.BOTTOM)),
    BLUE_LEFT_BOTTOM(new Position(10, 4), List.of(Direction.RIGHT_TOP, Direction.TOP, Direction.RIGHT)),
    BLUE_RIGHT_BOTTOM(new Position(10, 6), List.of(Direction.LEFT_TOP, Direction.TOP, Direction.LEFT)),
    BLUE_CENTER(new Position(9, 5), Direction.allDirections()),
    RED_LEFT_TOP(new Position(1, 4), List.of(Direction.RIGHT_BOTTOM, Direction.RIGHT, Direction.BOTTOM)),
    RED_RIGHT_TOP(new Position(1, 6), List.of(Direction.LEFT_BOTTOM, Direction.LEFT, Direction.BOTTOM)),
    RED_LEFT_BOTTOM(new Position(3, 4), List.of(Direction.RIGHT_TOP, Direction.TOP, Direction.RIGHT)),
    RED_RIGHT_BOTTOM(new Position(3, 6), List.of(Direction.LEFT_TOP, Direction.TOP, Direction.LEFT)),
    RED_CENTER(new Position(2, 5), Direction.allDirections()),
    ;

    private final Position position;
    private List<Direction> movableDirections;

    CastleArea(Position position, List<Direction> movableDirections) {
        this.position = position;
        this.movableDirections = movableDirections;
    }

    public List<Direction> filterCrossDirection() {
        return movableDirections.stream().filter(Direction::isCrossDirection).toList();
    }

    public static CastleArea fromByPosition(Position position) {
        return Arrays.stream(CastleArea.values())
                .filter(castleArea -> castleArea.position.equals(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치는 궁성이 아닙니다."));
    }

    public Position getPosition() {
        return position;
    }

    public static List<Position> fromCastleMovablePositions(Position position) {
        if (!isInnerCastle(position)) {
            throw new IllegalArgumentException("궁성 영역이 아닙니다.");
        }
        return castleDirection(position).stream()
                .filter(position::canMove)
                .map(position::move)
                .filter(CastleArea::isInnerCastle)
                .toList();
    }

    public static boolean isCrossMovableArea(Position position) {
        return Arrays.stream(values()).anyMatch(area -> area.position.equals(position));
    }

    public static boolean isInnerCastle(Position position) {
        int row = position.row();
        int column = position.column();
        return isBlueCastleArea(row, column) || isRedCastleArea(row, column);
    }

    private static List<Direction> castleDirection(Position position) {
        if (isCrossMovableArea(position)) {
            return fromByPosition(position).movableDirections;
        }
        return Direction.getStraightDirection();
    }

    private static boolean isBlueCastleArea(int row, int column) {
        return row >= 8 && row <= 10 && column >= 4 && column <= 6;
    }

    private static boolean isRedCastleArea(int row, int column) {
        return row >= 1 && row <= 3 && column >= 4 && column <= 6;
    }
}
