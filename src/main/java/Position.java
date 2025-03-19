public record Position(
    int x,
    int y
) {
    public Position move(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position move(Position other) {
        return new Position(x + other.x, y + other.y);
    }
}
