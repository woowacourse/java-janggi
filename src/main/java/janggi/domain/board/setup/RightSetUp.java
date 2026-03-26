package janggi.domain.board.setup;

import janggi.domain.coodinate.Point;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.Map;

public class RightSetUp implements BoardSetUp{
    @Override
    public Map<Point, Piece> generate(Side side) {
        Map<Point, Piece> leftSetup = new HashMap<>();

        if (side == Side.CHO){
            leftSetup.put(new Point(0, 1), new Horse(side));
            leftSetup.put(new Point(0, 2), new Elephant(side));
            leftSetup.put(new Point(0, 6), new Horse(side));
            leftSetup.put(new Point(0, 7), new Elephant(side));
        }

        if(side == Side.HAN){
            leftSetup.put(new Point(9, 7), new Horse(side));
            leftSetup.put(new Point(9, 6), new Elephant(side));
            leftSetup.put(new Point(9, 2), new Horse(side));
            leftSetup.put(new Point(9, 1), new Elephant(side));
        }
        return leftSetup;
    }
}
