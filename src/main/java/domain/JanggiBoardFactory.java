package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoardFactory {

    private static final List<Integer> rows = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static Map<Position, Piece> createJanggiBoard() {
        Map<Position, Piece> janggiBoard = new HashMap<>();
        for (Integer column : columns) {
            for (Integer row : rows) {
                janggiBoard.put(new Position(row, column), new Empty());
            }
        }
        init초JanggiBoard(janggiBoard);
        init한JanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void init초JanggiBoard(Map<Position, Piece> janggiBoard) {
        janggiBoard.put(new Position(9, 5), new 궁(Side.초));
        janggiBoard.put(new Position(0, 1), new 차(Side.초));
        janggiBoard.put(new Position(0, 9), new 차(Side.초));
        janggiBoard.put(new Position(8, 2), new 포(Side.초));
        janggiBoard.put(new Position(8, 8), new 포(Side.초));
        janggiBoard.put(new Position(7, 1), new 졸병(Side.초));
        janggiBoard.put(new Position(7, 3), new 졸병(Side.초));
        janggiBoard.put(new Position(7, 5), new 졸병(Side.초));
        janggiBoard.put(new Position(7, 7), new 졸병(Side.초));
        janggiBoard.put(new Position(7, 9), new 졸병(Side.초));
        janggiBoard.put(new Position(0, 4), new 사(Side.초));
        janggiBoard.put(new Position(0, 6), new 사(Side.초));
        janggiBoard.put(new Position(0, 2), new 마(Side.초));
        janggiBoard.put(new Position(0, 8), new 마(Side.초));
        janggiBoard.put(new Position(0, 3), new 상(Side.초));
        janggiBoard.put(new Position(0, 7), new 상(Side.초));
    }

    private static void init한JanggiBoard(Map<Position, Piece> janggiBoard) {
        janggiBoard.put(new Position(2, 5), new 궁(Side.한));
        janggiBoard.put(new Position(1, 1), new 차(Side.한));
        janggiBoard.put(new Position(1, 9), new 차(Side.한));
        janggiBoard.put(new Position(3, 2), new 포(Side.한));
        janggiBoard.put(new Position(3, 8), new 포(Side.한));
        janggiBoard.put(new Position(4, 1), new 졸병(Side.한));
        janggiBoard.put(new Position(4, 3), new 졸병(Side.한));
        janggiBoard.put(new Position(4, 5), new 졸병(Side.한));
        janggiBoard.put(new Position(4, 7), new 졸병(Side.한));
        janggiBoard.put(new Position(4, 9), new 졸병(Side.한));
        janggiBoard.put(new Position(1, 4), new 사(Side.한));
        janggiBoard.put(new Position(1, 6), new 사(Side.한));
        janggiBoard.put(new Position(1, 2), new 마(Side.한));
        janggiBoard.put(new Position(1, 8), new 마(Side.한));
        janggiBoard.put(new Position(1, 3), new 상(Side.한));
        janggiBoard.put(new Position(1, 7), new 상(Side.한));
    }
}
