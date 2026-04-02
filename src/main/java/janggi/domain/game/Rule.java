package janggi.domain.game;

import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collection;
import java.util.Map;

public interface Rule {
    boolean isEnd(Map<Point, Piece> pieces);

    Side getWinSide(Map<Point, Piece> pieces);

    default Collection<Piece> getPieces(Map<Point, Piece> pieces) {
        return pieces.values();
    }
}
