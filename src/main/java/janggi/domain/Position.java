package janggi.domain;

public record Position(int x, int y) {
    public Position move(Movement movement) {
        return new Position(x + movement.getDx(), y + movement.getDy());
    }
}
