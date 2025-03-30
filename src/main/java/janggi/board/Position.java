package janggi.board;

public record Position(int x, int y) {
    public int distanceX(Position other) {
        return Math.abs(this.x - other.x);
    }

    public int distanceY(Position other) {
        return Math.abs(this.y - other.y);
    }
}
