package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import java.util.List;

public interface Piece {

    List<Location> calculateRoute(Location from, Location to);

    void detectCollision(List<Piece> piecesOnPath);

    boolean isEmpty();

    boolean isPo();

    boolean isSameSide(Side side);

    PieceType getPieceType();

    Side getSide();
}
