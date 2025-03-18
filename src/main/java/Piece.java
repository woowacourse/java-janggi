import java.util.List;

public abstract class Piece {

    protected Position position;

    public Piece(int x, int y) {
        position = new Position(x, y);
    }

    public void move(int x, int y) {
        if (!canMove(x, y)) {
            throw new IllegalArgumentException();
        }
        position = position.move(x, y);
    }

    protected abstract boolean canMove(int x, int y);

    public Position getPosition() {
        return position;
    }

    protected record Route(
        List<Position> positions
    ) {

        public Position sum() {
            int sumX = positions.stream()
                .mapToInt(Position::x)
                .sum();
            int sumY = positions.stream()
                .mapToInt(Position::y)
                .sum();
            return new Position(sumX, sumY);
        }
    }
}
