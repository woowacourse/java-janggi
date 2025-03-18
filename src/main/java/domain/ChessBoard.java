package domain;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.List;

public class ChessBoard {

    private final Table<String, String, Piece> board = HashBasedTable.create();
    private final List<String> columns = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i");
    private final List<String> rows = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    public ChessBoard() {
        for (String column : columns) {
            for (String row : rows) {
                board.put(row, column, new Piece());
            }
        }
    }

    public Table<String, String, Piece> getBoard() {
        return board;
    }
}
