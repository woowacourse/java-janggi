package janggi.domain.piece.movement.fixed;

import janggi.domain.Position;

record Vector(int x, int y) {

    Position apply(Position origin, Position destination) {
        Direction direction = Direction.get(origin, destination);
        return new Position(origin.getX() + x * direction.getX(), origin.getY() + y * direction.getY());
    }

    boolean hasRelativeOffsetFrom(Position origin, Position destination) {
        int xDistance = origin.getXDistance(destination);
        int yDistance = origin.getYDistance(destination);
        return this.x() == xDistance && this.y() == yDistance;
    }
}
