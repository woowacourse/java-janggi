package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    protected final Side side;

    protected Piece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    public abstract List<Location> calculateRoute(Location from, Location to);

    public abstract void detectCollision(List<Piece> piecesOnPath);

    public boolean isEmpty() {
        return pieceType == PieceType.EMPTY;
    }

    public boolean isSameSide(Piece piece) {
        return this.side.equals(piece.side);
    }

    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
