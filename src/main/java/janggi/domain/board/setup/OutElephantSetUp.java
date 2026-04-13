package janggi.domain.board.setup;

import janggi.domain.piece.Piece;
import janggi.domain.piece.stepped.Elephant;
import janggi.domain.piece.stepped.Horse;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;

public class OutElephantSetUp implements BoardSetUp {
    public static final BoardSetUp INSTANCE = new OutElephantSetUp();

    @Override
    public Map<Point, Piece> generate(Side side) {
        Map<Point, Piece> setup = createCommonBoard(side);

        if (side == Side.CHO) {
            setup.put(new Point(0, 1), new Elephant(side));
            setup.put(new Point(0, 2), new Horse(side));
            setup.put(new Point(0, 6), new Horse(side));
            setup.put(new Point(0, 7), new Elephant(side));
        }

        if (side == Side.HAN) {
            setup.put(new Point(9, 7), new Elephant(side));
            setup.put(new Point(9, 6), new Horse(side));
            setup.put(new Point(9, 2), new Horse(side));
            setup.put(new Point(9, 1), new Elephant(side));
        }
        return setup;
    }

    @Override
    public String toString() {
        return "바깥상차림";
    }
}
