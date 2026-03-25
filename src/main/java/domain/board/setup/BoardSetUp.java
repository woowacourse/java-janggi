package domain.board.setup;

import domain.board.Point;
import domain.piece.Piece;
import domain.side.Side;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> generate(Side side);
}
