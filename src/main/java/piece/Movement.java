package piece;

import static piece.Direction.BOTTOM;
import static piece.Direction.LEFT;
import static piece.Direction.LEFT_BOTTOM;
import static piece.Direction.LEFT_TOP;
import static piece.Direction.RIGHT;
import static piece.Direction.RIGHT_BOTTOM;
import static piece.Direction.RIGHT_TOP;
import static piece.Direction.TOP;

import java.util.ArrayList;
import java.util.List;

import board.Position;

public enum Movement {

    TOP_LEFT_TOP(List.of(TOP, LEFT_TOP)),
    TOP_RIGHT_TOP(List.of(TOP, RIGHT_TOP)),
    BOTTOM_LEFT_BOTTOM(List.of(BOTTOM, LEFT_BOTTOM)),
    BOTTOM_RIGHT_BOTTOM(List.of(BOTTOM, RIGHT_BOTTOM)),
    LEFT_LEFT_TOP(List.of(LEFT, LEFT_TOP)),
    LEFT_LEFT_BOTTOM(List.of(LEFT, LEFT_BOTTOM)),
    RIGHT_RIGHT_TOP(List.of(RIGHT, RIGHT_TOP)),
    RIGHT_RIGHT_BOTTOM(List.of(RIGHT, RIGHT_BOTTOM)),

    TOP_LEFT_TOP_LEFT_TOP(List.of(TOP, LEFT_TOP, LEFT_TOP)),
    TOP_RIGHT_TOP_RIGHT_TOP(List.of(TOP, RIGHT_TOP, RIGHT_TOP)),
    BOTTOM_LEFT_BOTTOM_LEFT_BOTTOM(List.of(BOTTOM, LEFT_BOTTOM, LEFT_BOTTOM)),
    BOTTOM_RIGHT_BOTTOM_RIGHT_BOTTOM(List.of(BOTTOM, RIGHT_BOTTOM, RIGHT_BOTTOM)),
    LEFT_LEFT_TOP_LEFT_TOP(List.of(LEFT, LEFT_TOP, LEFT_TOP)),
    LEFT_LEFT_BOTTOM_LEFT_BOTTOM(List.of(LEFT, LEFT_BOTTOM, LEFT_BOTTOM)),
    RIGHT_RIGHT_TOP_RIGHT_TOP(List.of(RIGHT, RIGHT_TOP, RIGHT_TOP)),
    RIGHT_RIGHT_BOTTOM_RIGHT_BOTTOM(List.of(RIGHT, RIGHT_BOTTOM, RIGHT_BOTTOM)),
    ;

    private final List<Direction> directions;

    Movement(final List<Direction> directions) {
        this.directions = directions;
    }

    public List<Position> applyMovement(final Position position) {
        List<Position> positions = new ArrayList<>();
        Position movedPosition = position;
        for (Direction direction : this.directions) {
            movedPosition = movedPosition.moveByDirection(direction);
            positions.add(movedPosition);
        }
        return positions;
    }

}
