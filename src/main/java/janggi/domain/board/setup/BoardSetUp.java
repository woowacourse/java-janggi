package janggi.domain.board.setup;

import janggi.domain.piece.Piece;
import janggi.domain.piece.linear.Cannon;
import janggi.domain.piece.linear.Chariot;
import janggi.domain.piece.single.Advisor;
import janggi.domain.piece.single.General;
import janggi.domain.piece.single.Soldier;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> generate(Side side);

    default Map<Point, Piece> createCommonBoard(Side side) {
        if (Side.CHO.equals(side)) {
            return createChoCommonBoard();
        }
        if (Side.HAN.equals(side)) {
            return createHanCommonBoard();
        }
        throw new IllegalStateException("EMPTY SIDE는 보드 생성이 불가능합니다.");
    }

    private Map<Point, Piece> createHanCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(new Point(6, 8), new Soldier(Side.HAN));
        board.put(new Point(6, 6), new Soldier(Side.HAN));
        board.put(new Point(6, 4), new Soldier(Side.HAN));
        board.put(new Point(6, 2), new Soldier(Side.HAN));
        board.put(new Point(6, 0), new Soldier(Side.HAN));

        board.put(new Point(7, 7), new Cannon(Side.HAN));
        board.put(new Point(7, 1), new Cannon(Side.HAN));

        board.put(new Point(8, 4), new General(Side.HAN));

        board.put(new Point(9, 8), new Chariot(Side.HAN));
        board.put(new Point(9, 5), new Advisor(Side.HAN));
        board.put(new Point(9, 3), new Advisor(Side.HAN));
        board.put(new Point(9, 0), new Chariot(Side.HAN));

        return board;
    }

    private Map<Point, Piece> createChoCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(new Point(0, 0), new Chariot(Side.CHO));
        board.put(new Point(0, 3), new Advisor(Side.CHO));
        board.put(new Point(0, 5), new Advisor(Side.CHO));
        board.put(new Point(0, 8), new Chariot(Side.CHO));

        board.put(new Point(1, 4), new General(Side.CHO));

        board.put(new Point(2, 1), new Cannon(Side.CHO));
        board.put(new Point(2, 7), new Cannon(Side.CHO));

        board.put(new Point(3, 0), new Soldier(Side.CHO));
        board.put(new Point(3, 2), new Soldier(Side.CHO));
        board.put(new Point(3, 4), new Soldier(Side.CHO));
        board.put(new Point(3, 6), new Soldier(Side.CHO));
        board.put(new Point(3, 8), new Soldier(Side.CHO));
        return board;
    }

}
