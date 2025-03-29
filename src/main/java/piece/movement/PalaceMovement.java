package piece.movement;

import static piece.Direction.BOTTOM;
import static piece.Direction.LEFT;
import static piece.Direction.LEFT_BOTTOM;
import static piece.Direction.LEFT_TOP;
import static piece.Direction.RIGHT;
import static piece.Direction.RIGHT_BOTTOM;
import static piece.Direction.RIGHT_TOP;
import static piece.Direction.TOP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import board.Position;
import piece.Direction;

public enum PalaceMovement {

    PALACE_CENTER_POSITION(
            List.of(new Position(2, 5), new Position(9, 5)),
            List.of(LEFT, LEFT_TOP, LEFT_BOTTOM, RIGHT, RIGHT_TOP, RIGHT_BOTTOM, TOP, BOTTOM)
    ),
    PALACE_LEFT_TOP_VERTEX(
            List.of(new Position(1, 4), new Position(8, 4)),
            List.of(LEFT, RIGHT, TOP, BOTTOM, RIGHT_BOTTOM)
    ),
    PALACE_RIGHT_TOP_VERTEX(
            List.of(new Position(1, 6), new Position(8, 6)),
            List.of(LEFT, RIGHT, TOP, BOTTOM, LEFT_BOTTOM)
    ),
    PALACE_LEFT_BOTTOM_VERTEX(
            List.of(new Position(3, 4), new Position(10, 4)),
            List.of(LEFT, RIGHT, TOP, BOTTOM, RIGHT_TOP)
    ),
    PALACE_RIGHT_BOTTOM_VERTEX(
            List.of(new Position(3, 6), new Position(10, 6)),
            List.of(LEFT, RIGHT, TOP, BOTTOM, LEFT_TOP)
    ),
    PALACE_MID_POINT(
            List.of(
                    new Position(1, 5), new Position(2, 4), new Position(2, 6), new Position(3, 5),
                    new Position(8, 5), new Position(9, 4), new Position(9, 6), new Position(10, 5)
            ),
            List.of(LEFT, RIGHT, TOP, BOTTOM)
    );

    private final List<Position> palaceMovementPositions;
    private final List<Direction> directions;

    PalaceMovement(final List<Position> palaceMovementPositions, final List<Direction> directions) {
        this.palaceMovementPositions = palaceMovementPositions;
        this.directions = directions;
    }

    public static Set<Position> applyMovement(final Position position) {
        return Arrays.stream(values())
                .filter(palaceMovement -> palaceMovement.palaceMovementPositions.contains(position))
                .findFirst()
                .map(palaceMovement -> applyMovementDirections(position, palaceMovement))
                .orElse(Collections.emptySet());
    }

    private static Set<Position> applyMovementDirections(final Position position, final PalaceMovement palaceMovement) {
        return palaceMovement.directions
                .stream()
                .map(position::moveByDirection)
                .filter(movedPosition -> !movedPosition.isInValidPosition())
                .collect(Collectors.toSet());
    }

}
