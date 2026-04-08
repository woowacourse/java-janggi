package janggi.support;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;

public class TestPiece implements Piece {

    private final Side side;
    private final double score;

    public TestPiece(Side side, double score) {
        this.side = side;
        this.score = score;
    }

    public TestPiece(Side side) {
        this(side, 0);
    }

    @Override
    public List<Location> calculateRoute(Intersection from, Intersection to) {
        return List.of();
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        return;
    }

    @Override
    public boolean isSameSide(Piece piece) {
        return piece.isSameSide(side);
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }

    @Override
    public PieceType getType() {
        return null;
    }

    @Override
    public boolean isSame(PieceType pieceType) {
        return false;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public Side getSide() {
        return side;
    }
}
