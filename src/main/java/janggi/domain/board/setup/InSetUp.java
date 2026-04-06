package janggi.domain.board.setup;

import java.util.HashMap;
import java.util.Map;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

public class InSetUp implements BoardSetUp {

    @Override
    public Map<Point, Piece> generate(Side side) {
        Map<Point, Piece> setUp = new HashMap<>(createCommonBoard(side));

        if (side == Side.CHO) {
            setUp.put(Point.of(0, 1), new Horse(side));
            setUp.put(Point.of(0, 2), new Elephant(side));
            setUp.put(Point.of(0, 6), new Elephant(side));
            setUp.put(Point.of(0, 7), new Horse(side));
        }

        if (side == Side.HAN) {
            setUp.put(Point.of(9, 7), new Horse(side));
            setUp.put(Point.of(9, 6), new Elephant(side));
            setUp.put(Point.of(9, 2), new Elephant(side));
            setUp.put(Point.of(9, 1), new Horse(side));
        }
        return setUp;
    }
}
