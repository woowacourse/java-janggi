package view;

import domain.Team;
import domain.board.Point;
import domain.pieces.Piece;
import domain.pieces.PieceNames;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class OutputView {

    private static final int MAX_COLUMN = 9;
    private static final int MAX_ROW = 10;
    private static final List<Integer> BOARD_LINE_FEED_COLUMNS = List.of(2, 5);

    public void printError(String message) {
        System.out.println(message);
    }

    public void printTurnGuide() {
        System.out.println("""
                장기 게임에 오신걸 환영합니다.
                입력 순서는 초나라 -> 한나라 순서입니다.
                """);
    }

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
        builder.append("\n초나라는 한글, 한나라는 한자로 표시됩니다.");
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
            Optional.ofNullable(locations.get(point)).ifPresentOrElse(
                    piece -> builder.append(piece.getName()),
                    () -> builder.append(PieceNames.EMPTY.getNameForTeam(Team.NONE))
            );
            builder.append(" ");
            addBoardLinefeed(builder, column);
        }
    }

    private void addBoardLinefeed(final StringBuilder builder, final int column) {
        if (BOARD_LINE_FEED_COLUMNS.contains(column)) {
            builder.append(" ");
        }
    }
}
