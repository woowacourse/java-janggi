package view;

import domain.board.Point;
import domain.pieces.Piece;
import domain.player.Score;
import domain.player.Team;
import dto.Choice;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public final class OutputView {
    private static final List<Integer> BOARD_LINE_FEED_COLUMNS = List.of(2, 5);

    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String DOUBLE_SPACE = "  ";

    private static final int MAX_COLUMN = 9;
    private static final int MAX_ROW = 10;

    public void printError(final String message) {
        System.err.println("[ERROR] " + message);
    }

    public void printWarring(final String message) {
        System.out.println(message);
    }

    public void printActivateGames(final List<Integer> gameIds) {
        final StringBuilder builder = new StringBuilder();
        for (Integer gameId : gameIds) {
            builder.append(gameId)
                    .append("번 게임방이 진행 중입니다!")
                    .append(NEW_LINE);
        }
        System.out.println(builder);
    }

    public void printTurnGuide() {
        System.out.println("""
                활성화된 방이 없습니다.
                새로운 게임을 시작합니다!
                
                장기 게임에 오신걸 환영합니다.
                입력 순서는 초나라 -> 한나라 순서입니다.
                """);
    }

    public void printLoadGame(final Choice choice) {
        System.out.println(choice.value() + "번 게임방을 불러오는 중입니다.");
    }

    public void printBoard(final Map<Point, Piece> locations) {
        System.out.println(boardToString(locations));
    }

    public void printWinner(final Team team) {
        System.out.println(team + "가 승리했습니다!");
    }

    public void printScores(final Map<Team, Score> scores) {
        for (final Entry<Team, Score> scoresByTeam : scores.entrySet()) {
            final Team team = scoresByTeam.getKey();
            final Score score = scoresByTeam.getValue();
            System.out.printf("%s : %.1f 점" + NEW_LINE, team, score.value());
        }

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
