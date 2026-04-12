package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Side side;

    protected Piece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    public abstract List<Location> calculateRoute(Location from, Location to);

    public abstract void detectCollision(List<Piece> piecesOnPath);

    public boolean isEmpty() {
        return pieceType.equals(PieceType.EMPTY);
    }

    public boolean isPo() {
        return pieceType.equals(PieceType.PO);
    }

    public boolean isGung() {
        return pieceType.equals(PieceType.GUNG);
    }

    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }
}
