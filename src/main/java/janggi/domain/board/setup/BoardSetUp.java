package janggi.domain.board.setup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;

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
        board.put(Point.of(6, 8), new Soldier(Side.HAN));
        board.put(Point.of(6, 6), new Soldier(Side.HAN));
        board.put(Point.of(6, 4), new Soldier(Side.HAN));
        board.put(Point.of(6, 2), new Soldier(Side.HAN));
        board.put(Point.of(6, 0), new Soldier(Side.HAN));

        board.put(Point.of(7, 7), new Cannon(Side.HAN));
        board.put(Point.of(7, 1), new Cannon(Side.HAN));

        board.put(Point.of(8, 4), new General(Side.HAN));

        board.put(Point.of(9, 8), new Chariot(Side.HAN));
        board.put(Point.of(9, 5), new Advisor(Side.HAN));
        board.put(Point.of(9, 3), new Advisor(Side.HAN));
        board.put(Point.of(9, 0), new Chariot(Side.HAN));

        return Collections.unmodifiableMap(board);
    }

    private Map<Point, Piece> createChoCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(Point.of(0, 0), new Chariot(Side.CHO));
        board.put(Point.of(0, 3), new Advisor(Side.CHO));
        board.put(Point.of(0, 5), new Advisor(Side.CHO));
        board.put(Point.of(0, 8), new Chariot(Side.CHO));

        board.put(Point.of(1, 4), new General(Side.CHO));

        board.put(Point.of(2, 1), new Cannon(Side.CHO));
        board.put(Point.of(2, 7), new Cannon(Side.CHO));

        board.put(Point.of(3, 0), new Soldier(Side.CHO));
        board.put(Point.of(3, 2), new Soldier(Side.CHO));
        board.put(Point.of(3, 4), new Soldier(Side.CHO));
        board.put(Point.of(3, 6), new Soldier(Side.CHO));
        board.put(Point.of(3, 8), new Soldier(Side.CHO));
        return Collections.unmodifiableMap(board);
    }

}
