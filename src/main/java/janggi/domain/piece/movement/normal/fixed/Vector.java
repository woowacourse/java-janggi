package janggi.domain.piece.movement.normal.fixed;

import janggi.domain.piece.Position;

record Vector(int x, int y) {

    Position apply(Position origin, Position destination) {
        Direction direction = Direction.get(origin, destination);
        return origin.plus(x * direction.getX(), y * direction.getY());
    }

    boolean hasRelativeOffsetFrom(Position origin, Position destination) {
        return origin.hasXDistance(destination, x()) && origin.hasYDistance(destination, y());
    }
}
