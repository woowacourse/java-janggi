package janggi.support;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;

public class TestPiece implements Piece {

    private final PieceType pieceType;
    private final Side side;

    public TestPiece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isPo() {
        return this.pieceType.equals(PieceType.PO);
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return List.of();
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        return;
    }
}
