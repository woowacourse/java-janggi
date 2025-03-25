package model;

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
}
