package model;

import model.piece.movement.PalaceMovement;

public record Position(
    int x,
    int y
) {

    public Position move(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position difference(Position other) {
        return new Position(x - other.x, y - other.y);
    }

    public Position multiply(Position other) {
        return new Position(x * other.x, y * other.y);
    }

    public Position toDirection() {
        int max = Math.max(Math.abs(x), Math.abs(y));
        return new Position(x / max, y / max);
    }

    public boolean betweenOnPalaceDiagonal(Team team, Position other) {
        if (Math.abs(difference(other).x) <= 2 && Math.abs(difference(other).y) <= 2
            && (PalaceMovement.isInPalace(team, other))) {
            Position difference = difference(other);
            return Math.abs(difference.toDirection().x) == 1 && Math.abs(difference.toDirection().y) == 1;
        }
        return false;
    }
}
