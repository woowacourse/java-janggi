package janggi.domain.piece;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.Side;
import java.util.List;

public interface Piece {

    boolean isEmpty();

    boolean isNotEmpty();

    List<Location> calculateRoute(Intersection from, Intersection to);

    void detectCollision(List<Piece> piecesOnPath);

    boolean isSameSide(Piece piece);

    boolean isSameSide(Side side);

    PieceType getType();

    boolean isSame(PieceType pieceType);

    double getScore();

    Side getSide();
}
