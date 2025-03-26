package view;

import domain.Team;
import domain.board.Point;
import domain.pieces.Piece;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class OutputView {

    private static final List<Integer> BOARD_LINE_FEED_COLUMNS = List.of(2, 5);

    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String DOUBLE_SPACE = "  ";

    private static final int MAX_COLUMN = 9;
    private static final int MAX_ROW = 10;

    public void printError(final String message) {
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

    public void printWinner(final Team currentTeam) {
        System.out.println(currentTeam.toString() + "가 승리했습니다!");
    }

    private String boardToString(final Map<Point, Piece> locations) {
        final StringBuilder builder = new StringBuilder();
        writeColumnGuideLine(builder);

        for (int row = MAX_ROW - 1; row >= 0; row--) {
            builder.append((char) ('A' + (MAX_ROW - 1 - row))).append(SPACE);
            addPieceName(locations, row, builder);
            builder.append(NEW_LINE);
        }
        builder.append(NEW_LINE).append("초나라는 한글, 한나라는 한자로 표시됩니다.");
        return builder.toString();
    }

    private void writeColumnGuideLine(final StringBuilder builder) {
        for (int column = 0; column < MAX_COLUMN; column++) {
            builder.append(DOUBLE_SPACE);
            builder.append(column);
        }
        builder.append(NEW_LINE);
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
                    () -> builder.append("―")
            );
            builder.append(SPACE);
            addBoardColumnLinefeed(builder, column);
        }
    }

    private void addBoardColumnLinefeed(final StringBuilder builder, final int column) {
        if (BOARD_LINE_FEED_COLUMNS.contains(column)) {
            builder.append(SPACE);
        }
    }

}
