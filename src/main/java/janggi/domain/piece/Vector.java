package janggi.domain.piece;

import janggi.domain.Position;

public record Vector(int x, int y) {

    public Position apply(Position origin, Position destination) {
        Direction direction = Direction.get(origin, destination);
        return new Position(origin.getX() + x * direction.getX(), origin.getY() + y * direction.getY());
    }

    public boolean hasRelativeOffsetFrom(Position origin, Position destination) {
        int xDistance = origin.getXDistance(destination);
        int yDistance = origin.getYDistance(destination);
        return this.x() == xDistance && this.y() == yDistance;
    }
}
