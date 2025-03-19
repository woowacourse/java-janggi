package domain;

public class Position {

    private final Point point;
    private final Piece piece;

    public Position(final Point point, final Piece piece) {
        this.point = point;
        this.piece = piece;
    }
}
