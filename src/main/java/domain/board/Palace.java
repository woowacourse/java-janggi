package domain.board;

import domain.position.LineDirection;
import domain.position.Position;

import java.util.Arrays;
import java.util.List;

public enum Palace {

    UP(new Position(5, 2)),
    DOWN(new Position(5, 9));

    private final Position position;

    Palace(Position position) {
        this.position = position;
    }

    public static Palace from(LineDirection direction) {
        return direction == LineDirection.UP ? UP : DOWN;
    }

    public boolean isBound(int x, int y) {
        int diffX = Math.abs(this.position.x() - x);
        int diffY = Math.abs(this.position.y() - y);
        if (diffX <= 1 && diffY <= 1) {
            return true;
        }
        return false;
    }

    public static List<Position> getAllPositions() {
        return Arrays.stream(Palace.values())
                .map(Palace::getPosition)
                .toList();
    }

    public Position getPosition() {
        return position;
    }
}
