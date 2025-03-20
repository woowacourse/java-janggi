package view;

import domain.board.Point;
import domain.pieces.Piece;
import java.util.Map;

public final class OutputView {

    private static final int MAX_COLUMN = 9;
    private static final int MAX_ROW = 10;

    public void printBoard(final Map<Point, Piece> locations) {
        System.out.println(boardToString(locations));
    }

    private String boardToString(final Map<Point, Piece> locations) {
        final StringBuilder result = new StringBuilder();
        result.append("  ");
        for (int column = 0; column < MAX_COLUMN; column++) {
            result.append(column).append(" ");
        }
        result.append("\n");

        for (int row = MAX_ROW - 1; row >= 0; row--) {
            result.append((char) ('9' - (MAX_ROW - 1 - row))).append(" ");
            addPieceName(locations, row, result);
            result.append("\n");
        }

        return result.toString();
    }

    private void addPieceName(final Map<Point, Piece> locations, final int row,
                              StringBuilder result) {
        for (int column = 0; column < MAX_COLUMN; column++) {
            final Point point = new Point(row, column);
            final Piece piece = locations.get(point);
            result.append(piece.getName());
            result.append(" ");
        }
    }
}
