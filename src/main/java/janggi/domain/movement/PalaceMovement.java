package janggi.domain.movement;

import java.util.Arrays;
import java.util.List;

public enum PalaceMovement {

    HAN_CORNER1(Position.of(1, 4), List.of(Direction.DOWN, Direction.RIGHT, Direction.RIGHT_DOWN)),
    HAN_CORNER2(Position.of(1, 6), List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT_DOWN)),
    HAN_CORNER3(Position.of(3, 4), List.of(Direction.RIGHT, Direction.UP, Direction.RIGHT_UP)),
    HAN_CORNER4(Position.of(3, 6), List.of(Direction.LEFT, Direction.UP, Direction.LEFT_UP)),
    HAN_MIDPOINT1(Position.of(1, 5), List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT)),
    HAN_MIDPOINT2(Position.of(2, 4), List.of(Direction.DOWN, Direction.UP, Direction.RIGHT)),
    HAN_MIDPOINT3(Position.of(2, 6), List.of(Direction.DOWN, Direction.UP, Direction.LEFT)),
    HAN_MIDPOINT4(Position.of(3, 5), List.of(Direction.LEFT, Direction.UP, Direction.RIGHT)),
    HAN_CENTER(Position.of(2, 5), List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT,
            Direction.LEFT_DOWN, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.RIGHT_UP)),
    CHO_CORNER1(Position.of(8, 4), List.of(Direction.DOWN, Direction.RIGHT, Direction.RIGHT_DOWN)),
    CHO_CORNER2(Position.of(8, 6), List.of(Direction.DOWN, Direction.LEFT, Direction.LEFT_DOWN)),
    CHO_CORNER3(Position.of(10, 4), List.of(Direction.RIGHT, Direction.UP, Direction.RIGHT_UP)),
    CHO_CORNER4(Position.of(10, 6), List.of(Direction.LEFT, Direction.UP, Direction.LEFT_UP)),
    CHO_MIDPOINT1(Position.of(8, 5), List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT)),
    CHO_MIDPOINT2(Position.of(9, 4), List.of(Direction.DOWN, Direction.UP, Direction.RIGHT)),
    CHO_MIDPOINT3(Position.of(9, 6), List.of(Direction.DOWN, Direction.UP, Direction.LEFT)),
    CHO_MIDPOINT4(Position.of(10, 5), List.of(Direction.LEFT, Direction.UP, Direction.RIGHT)),
    CHO_CENTER(Position.of(9, 5), List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT,
            Direction.LEFT_DOWN, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.RIGHT_UP)),
    ;

    private static final List<PalaceMovement> PALACE_CORNERS = List.of(
            PalaceMovement.HAN_CORNER1,
            PalaceMovement.HAN_CORNER2,
            PalaceMovement.HAN_CORNER3,
            PalaceMovement.HAN_CORNER4,
            PalaceMovement.CHO_CORNER1,
            PalaceMovement.CHO_CORNER2,
            PalaceMovement.CHO_CORNER3,
            PalaceMovement.CHO_CORNER4
    );

    private final Position position;
    private final List<Direction> directions;

    PalaceMovement(Position position, List<Direction> directions) {
        this.position = position;
        this.directions = directions;
    }

    public static boolean isInsidePalace(Position currentPosition) {
        return Arrays.stream(PalaceMovement.values())
                .anyMatch(movement -> movement.position.equals(currentPosition));
    }

    public static PalaceMovement getPalaceMovement(Position position) {
        return Arrays.stream(PalaceMovement.values())
                .filter(palaceMovement -> palaceMovement.position.equals(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 궁성을 이루는 좌표가 아닙니다."));
    }

    public static List<Direction> getDirectionsAtPosition(Position currentPosition) {
        return getPalaceMovement(currentPosition).directions;
    }

    public static boolean isCorner(Position position) {
        PalaceMovement palaceMovement = getPalaceMovement(position);
        return PALACE_CORNERS.contains(palaceMovement);
    }
}
