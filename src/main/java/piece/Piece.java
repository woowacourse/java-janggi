package piece;

import direction.Point;

public abstract class Piece {

    protected final PieceType type;
    protected Point current;

    public Piece(PieceType type, Point current) {
        this.type = type;
        this.current = current;
    }

    public abstract void move(final Pieces allPieces, final Point destination);

    public int killableToKill(Pieces oppositeTeamPieces) {
        return oppositeTeamPieces.diePieceInPoint(current);
    }

    public boolean isSamePoint(final Point point) {
        return current.equals(point);
    }

    public boolean isSameType(final Piece piece) {
        return type.equals(piece.type);
    }

    public abstract int score();

    public PieceType type() {
        return type;
    }

    public int row() {
        return current.row();
    }

    public int column() {
        return current.column();
    }
}
