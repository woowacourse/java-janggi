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
    final StringBuilder builder = new StringBuilder();
    writeColumnGuideLine(builder);

    for (int row = MAX_ROW - 1; row >= 0; row--) {
      builder.append((char) ('A' + (MAX_ROW - 1 - row))).append(" ");
      addPieceName(locations, row, builder);
      builder.append("\n");
    }

    return builder.toString();
  }

  private void writeColumnGuideLine(final StringBuilder builder) {
    for (int column = 0; column < MAX_COLUMN; column++) {
      builder.append("  ");
      builder.append(column);
    }
    builder.append("\n");
  }

  private void addPieceName(
      final Map<Point, Piece> locations,
      final int row,
      final StringBuilder builder
  ) {
    for (int column = 0; column < MAX_COLUMN; column++) {
      final Point point = new Point(row, column);
      final Piece piece = locations.get(point);
      builder.append(piece.getName());
      builder.append(" ");
      if (column == 2 || column == 5) {
        builder.append(" ");
      }
    }
  }
}
