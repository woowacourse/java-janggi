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
                board.put(row, column, new Piece());
            }
        }
    }

    public Table<Integer, Integer, Piece> getBoard() {
        return board;
    }

    public void initBoard() {
        board.put(9, 5, new 궁());
        board.put(0, 1, new 차());
        board.put(0, 9, new 차());
        board.put(8, 2, new 포());
        board.put(8, 8, new 포());
        board.put(7, 1, new 졸병());
        board.put(7, 3, new 졸병());
        board.put(7, 5, new 졸병());
        board.put(7, 7, new 졸병());
        board.put(7, 9, new 졸병());
        board.put(0, 4, new 사());
        board.put(0, 6, new 사());
    }

    public Piece getPieceFrom(int row, int column) {
        return board.get(row, column);
    }
}
