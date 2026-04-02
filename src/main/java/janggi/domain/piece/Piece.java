package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public interface Piece {

    boolean isEmpty();

    List<Location> calculateRoute(Location from, Location to);

    void detectCollision(List<Piece> piecesOnPath);

    boolean isSameSide(Piece piece);

    boolean isSameSide(Side side);

    PieceType getType();

    boolean isSame(PieceType pieceType);
}
