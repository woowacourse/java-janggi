package domain;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.List;

public class JanggiBoardFactory {

    private static final List<Integer> rows = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private static final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static Table<Integer, Integer, Piece> createJanggiBoard() {
        Table<Integer, Integer, Piece> janggiBoard = HashBasedTable.create();
        for (Integer column : columns) {
            for (Integer row : rows) {
                janggiBoard.put(row, column, new Empty());
            }
        }
        init초JanggiBoard(janggiBoard);
        init한JanggiBoard(janggiBoard);
        return janggiBoard;
    }

    private static void init초JanggiBoard(Table<Integer, Integer, Piece> janggiBoard) {
        janggiBoard.put(9, 5, new 궁(Side.초));
        janggiBoard.put(0, 1, new 차(Side.초));
        janggiBoard.put(0, 9, new 차(Side.초));
        janggiBoard.put(8, 2, new 포(Side.초));
        janggiBoard.put(8, 8, new 포(Side.초));
        janggiBoard.put(7, 1, new 졸병(Side.초));
        janggiBoard.put(7, 3, new 졸병(Side.초));
        janggiBoard.put(7, 5, new 졸병(Side.초));
        janggiBoard.put(7, 7, new 졸병(Side.초));
        janggiBoard.put(7, 9, new 졸병(Side.초));
        janggiBoard.put(0, 4, new 사(Side.초));
        janggiBoard.put(0, 6, new 사(Side.초));
        janggiBoard.put(0, 2, new 마(Side.초));
        janggiBoard.put(0, 8, new 마(Side.초));
        janggiBoard.put(0, 3, new 상(Side.초));
        janggiBoard.put(0, 7, new 상(Side.초));
    }

    private static void init한JanggiBoard(Table<Integer, Integer, Piece> janggiBoard) {
        janggiBoard.put(2, 5, new 궁(Side.한));
        janggiBoard.put(1, 1, new 차(Side.한));
        janggiBoard.put(1, 9, new 차(Side.한));
        janggiBoard.put(3, 2, new 포(Side.한));
        janggiBoard.put(3, 8, new 포(Side.한));
        janggiBoard.put(4, 1, new 졸병(Side.한));
        janggiBoard.put(4, 3, new 졸병(Side.한));
        janggiBoard.put(4, 5, new 졸병(Side.한));
        janggiBoard.put(4, 7, new 졸병(Side.한));
        janggiBoard.put(4, 9, new 졸병(Side.한));
        janggiBoard.put(1, 4, new 사(Side.한));
        janggiBoard.put(1, 6, new 사(Side.한));
        janggiBoard.put(1, 2, new 마(Side.한));
        janggiBoard.put(1, 8, new 마(Side.한));
        janggiBoard.put(1, 3, new 상(Side.한));
        janggiBoard.put(1, 7, new 상(Side.한));
    }
}
