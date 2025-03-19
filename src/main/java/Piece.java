import java.util.List;

public abstract class Piece {

    private Team team;
    protected Position position;

    protected Piece(int x, int y, Team team) {
        this.team = team;
        position = new Position(x, y);
    }

    public void move(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        if (!board.isInboard(target)) {
            throw new IllegalArgumentException();
        }
        if (!canMove(board, dx, dy)) {
            throw new IllegalArgumentException();
        }
        arrival(board, target);
        position = target;
    }

    protected abstract boolean canMove(Board board, int dx, int dy);

    public void arrival(Board board, Position target) {
        if (!board.hasPieceOn(target)) {
            return;
        }
        Piece targetPiece = board.get(target);
        if (targetPiece.team == team) {
            throw new IllegalArgumentException();
        } else {
            board.take(targetPiece);
        }
    }

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
