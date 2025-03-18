package domain;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.List;

public class ChessBoard {

    private final Table<Integer, Integer, Piece> board = HashBasedTable.create();
    private final List<Integer> rows = List.of(0, 9, 8, 7, 6, 5, 4, 3, 2, 1);
    private final List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public ChessBoard() {
        for (Integer column : columns) {
            for (Integer row : rows) {
                board.put(row, column, new Empty());
            }
        }
    }

    public Table<Integer, Integer, Piece> getBoard() {
        return board;
    }

    public void init초Board() {
        board.put(9, 5, new 궁(Side.초));
        board.put(0, 1, new 차(Side.초));
        board.put(0, 9, new 차(Side.초));
        board.put(8, 2, new 포(Side.초));
        board.put(8, 8, new 포(Side.초));
        board.put(7, 1, new 졸병(Side.초));
        board.put(7, 3, new 졸병(Side.초));
        board.put(7, 5, new 졸병(Side.초));
        board.put(7, 7, new 졸병(Side.초));
        board.put(7, 9, new 졸병(Side.초));
        board.put(0, 4, new 사(Side.초));
        board.put(0, 6, new 사(Side.초));
        board.put(0, 2, new 마(Side.초));
        board.put(0, 8, new 마(Side.초));
        board.put(0, 3, new 상(Side.초));
        board.put(0, 7, new 상(Side.초));
    }

    public void init한Board() {
        board.put(2, 5, new 궁(Side.한));
        board.put(1, 1, new 차(Side.한));
        board.put(1, 9, new 차(Side.한));
        board.put(3, 2, new 포(Side.한));
        board.put(3, 8, new 포(Side.한));
        board.put(4, 1, new 졸병(Side.한));
        board.put(4, 3, new 졸병(Side.한));
        board.put(4, 5, new 졸병(Side.한));
        board.put(4, 7, new 졸병(Side.한));
        board.put(4, 9, new 졸병(Side.한));
        board.put(1, 4, new 사(Side.한));
        board.put(1, 6, new 사(Side.한));
        board.put(1, 2, new 마(Side.한));
        board.put(1, 8, new 마(Side.한));
        board.put(1, 3, new 상(Side.한));
        board.put(1, 7, new 상(Side.한));
    }

    public Piece getPieceFrom(int row, int column) {
        return board.get(row, column);
    }
}
