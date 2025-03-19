package view;

import domain.board.Board;
import domain.board.Point;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class OutputView {

    public static void printBoard(Board board) {
        List<List<String>> boardString = new ArrayList<>();
        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {
            List<String> rowString = new ArrayList<>();
            boardString.add(rowString);

            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                Point point = Point.of(row, column);
                if (!board.existsPiece(point)) {
                    rowString.add(paintColor(Painter.paintWhite(), "ㅁ"));
                    continue;
                }
                Piece piece = board.findPieceByPoint(point);
                rowString.add(
                        paintColor(Painter.paintByTeam(piece.team()), piece.type().getTitle())
                );
            }
        }

        for (List<String> rowString : boardString) {
            System.out.println(String.join(" ", rowString));
        }
    }

    private static String paintColor(String color, String text) {
        return color + text;
    }
}
