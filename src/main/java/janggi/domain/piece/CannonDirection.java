package janggi.domain.piece;

public enum CannonDirection {
    UP(new Position(-1, 0)),
    DOWN(new Position(1, 0)),
    LEFT(new Position(0, -1)),
    RIGHT(new Position(0, 1));
    private final Position direction;

    CannonDirection(Position direction) {
        this.direction = direction;
    }

    public static Position getDirection(Position relativePosition) {
        if(relativePosition.x() < 0) {
            return CannonDirection.UP.direction;
        }
        if(relativePosition.x() > 0) {
            return CannonDirection.DOWN.direction;
        }
        if(relativePosition.y() < 0) {
            return CannonDirection.LEFT.direction;
        }
        if(relativePosition.y() > 0) {
            return CannonDirection.RIGHT.direction;
        }
        throw new IllegalArgumentException();
    }
}
