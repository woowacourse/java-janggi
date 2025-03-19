import java.util.List;

public abstract class Piece {

    protected Position position;

    public Piece(int x, int y) {
        position = new Position(x, y);
    }

    public void move(Board board, int dx, int dy) {
        if (!canMove(board, dx, dy)) {
            throw new IllegalArgumentException();
        }
        position = position.move(dx, dy);
    }

    protected abstract boolean canMove(Board board, int dx, int dy);

    public Position getPosition() {
        return position;
    }

    public boolean onPosition(Position nextPos) {
        return position.equals(nextPos);
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
