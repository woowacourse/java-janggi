package view;

import domain.board.Point;
import domain.pieces.Piece;
import java.util.Map;

public class OutputView {

    public void printBoard(Map<Point, Piece> locations) {
        System.out.println(boardToString(locations));
    }

    private String boardToString(Map<Point, Piece> locations) {
        int maxColumn = 9;
        int maxRow = 10;

        StringBuilder result = new StringBuilder();
        result.append("  ");
        for (int column = 1; column <= maxColumn; column++) {
            result.append(column).append(" ");
        }
        result.append("\n");

        for (int row = maxRow - 1; row >= 0; row--) {
            result.append((char) ('A' + (maxRow - 1 - row))).append(" ");
            addPieceName(locations, maxColumn, row, result);
            result.append("\n");
        }

        return result.toString();
    }

    private void addPieceName(Map<Point, Piece> locations, int maxColumn, int row, StringBuilder result) {
        for (int column = 0; column < maxColumn; column++) {
            Point point = new Point(row, column);
            Piece piece = locations.get(point);
            result.append(piece.getName());
            result.append(" ");
        }
    }
}
