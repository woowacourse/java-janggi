package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.exception.RouteResolveException;
import java.util.List;

public abstract class ActivePiece implements Piece {

    protected final PieceType pieceType;
    protected final Side side;

    protected ActivePiece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    @Override
    public final List<Location> calculateRoute(Intersection from, Intersection to) {
        try {
            return resolveRoute(from, to);
        } catch (RouteResolveException e) {
            throw new RouteResolveException(pieceType, from.getLocation(), to.getLocation());
        }
    }

    protected abstract List<Location> resolveRoute(Intersection from, Intersection to);

    @Override
    public PieceType getType() {
        return pieceType;
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
    public boolean isSame(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    @Override
    public double getScore() {
        return this.pieceType.getScore();
    }

    @Override
    public Side getSide() {
        return this.side;
    }
}
