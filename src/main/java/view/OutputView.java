package view;

import domain.board.Board;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;

public class OutputView {

    public static void printBoard(Board board) {
        List<List<String>> mapString = new ArrayList<>();
        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            List<String> columnString = new ArrayList<>();
            mapString.add(columnString);

            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                if (!board.existsPiece(point)) {
                    columnString.add("ㅁ");
                    continue;
                }
                columnString.add(board.findPieceByPoint(point).type().getTitle());
            }
        }

        for (List<String> rowString : mapString) {
            System.out.println(String.join(" ", rowString));
        }
    }
}
