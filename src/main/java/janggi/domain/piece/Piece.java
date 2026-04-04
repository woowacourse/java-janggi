package janggi.domain.piece;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public interface Piece {

    boolean isEmpty();

    List<Location> calculateRoute(Intersection from, Intersection to);

    void detectCollision(List<Piece> piecesOnPath);

    boolean isSameSide(Piece piece);

    boolean isSameSide(Side side);

    PieceType getType();

    boolean isSame(PieceType pieceType);
}
