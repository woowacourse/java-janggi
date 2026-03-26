package janggi.domain.board.setup;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> generate(Side side);
}
