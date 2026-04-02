package domain.coordinate;

public record Position(int col, int row) {

    public Position nextPosition(Direction direction) {
        return new Position(col + direction.getCol(), row + direction.getRow());
    }
}
