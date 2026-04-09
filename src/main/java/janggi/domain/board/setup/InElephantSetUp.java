package janggi.domain.board.setup;

import janggi.domain.piece.Piece;
import janggi.domain.piece.fixed.Elephant;
import janggi.domain.piece.fixed.Horse;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;

public class InElephantSetUp implements BoardSetUp {

    @Override
    public Map<Point, Piece> generate(Side side) {
        Map<Point, Piece> setUp = createCommonBoard(side);

        if (side == Side.CHO) {
            setUp.put(new Point(0, 1), new Horse(side));
            setUp.put(new Point(0, 2), new Elephant(side));
            setUp.put(new Point(0, 6), new Elephant(side));
            setUp.put(new Point(0, 7), new Horse(side));
        }

        if (side == Side.HAN) {
            setUp.put(new Point(9, 7), new Horse(side));
            setUp.put(new Point(9, 6), new Elephant(side));
            setUp.put(new Point(9, 2), new Elephant(side));
            setUp.put(new Point(9, 1), new Horse(side));
        }
        return setUp;
    }

    @Override
    public String toString() {
        return "안상차림";
    }
}
