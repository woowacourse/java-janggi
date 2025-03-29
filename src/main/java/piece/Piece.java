package piece;

import direction.Point;

public abstract class Piece {

    protected Point current;

    public Piece() {
    }

    public Piece(final Point current) {
        this.current = current;
    }

    public abstract void move(final Pieces allPieces, final Point destination);

    public boolean isSamePoint(final Point point) {
        return current.equals(point);
    }

    public abstract int score();

    public int killableToKill(Pieces oppositeTeamPieces) {
        return oppositeTeamPieces.diePieceInPoint(current);
    }

        return object != null && getClass() == object.getClass();
    }
}
