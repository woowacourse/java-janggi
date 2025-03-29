package location;

public record Position(int x, int y) {

    public Position apply(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }
}
