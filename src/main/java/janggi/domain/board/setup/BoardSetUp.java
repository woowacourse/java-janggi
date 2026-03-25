package janggi.domain.board.setup;

import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import janggi.domain.side.Side;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> generate(Side side);
}
