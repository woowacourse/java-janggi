package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Point;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> pieces();
}
