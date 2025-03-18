package domain.piece;

public abstract class Piece {

    private final Position position;

    public Piece(final int row, final int column) {
        this.position = new Position(row, column);
    }

    public Position getPosition() {
        return position;
    }

    public abstract String getName();
}
