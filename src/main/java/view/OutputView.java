package view;

import java.util.Map;
import java.util.stream.IntStream;

import board.Position;
import piece.Piece;
import piece.Team;

public class OutputView {

    private static final String red = "\u001B[31m";
    private static final String blue = "\u001B[34m";
    private static final String exit = "\u001B[0m";
    private static final String BLANK_CELL = "ㅁ";

    public void printBoard(final Map<Position, Piece> pieces) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        appendHeader(stringBuilder);
        IntStream.range(1, 11).forEach(rowIndex -> appendRow(pieces, stringBuilder, rowIndex));
        stringBuilder.print();
    }

    private void appendRow(final Map<Position, Piece> pieces, final CustomStringBuilder stringBuilder,
                           final int rowIndex) {
        stringBuilder.append(String.valueOf(rowIndex % 10));
        appendBoardByRow(pieces, stringBuilder, rowIndex);
        stringBuilder.lineSplit();
    }

    private void appendBoardByRow(final Map<Position, Piece> pieces, final CustomStringBuilder stringBuilder,
                                  final int rowIndex) {
        IntStream.range(1, 10).forEach(columnIndex -> stringBuilder.append(getCellContent(
                pieces,
                new Position(rowIndex, columnIndex))
        ));
    }

    private String getCellContent(final Map<Position, Piece> pieces, final Position position) {
        if (pieces.containsKey(position)) {
            Piece piece = pieces.get(position);
            return convertContentColor(piece.getTeam(), piece.getType().getName());
        }
        return BLANK_CELL;
    }

    public void printTeamScore(final Map<Team, Double> teamScore) {
        teamScore.forEach((key, value) -> System.out.printf("%s: %.1f점%n", key, value));
        System.out.println();
    }

    private void appendHeader(final CustomStringBuilder stringBuilder) {
        stringBuilder.appendBlankCell();
        IntStream.range(1, 10).forEach(columnIndex -> stringBuilder.appendHeader(
                toFullWidthCharacter(String.valueOf(columnIndex))
        ));
        stringBuilder.lineSplit();
    }

    public void printError(final String content) {
        System.out.println(String.join(" ", "[ERROR]", content));
    }

    private String convertContentColor(final Team team, final String content) {
        if (team == Team.BLUE) {
            return String.format("%s%s%s", blue, content, exit);
        }
        return String.format("%s%s%s", red, content, exit);
    }

    public char toFullWidthCharacter(final String number) {
        char c = number.charAt(0);
        return (char) (c + 0xfee0);
    }

    public void printWinner(final Team winnerTeam) {
        System.out.printf("%s의 승리입니다.%n", winnerTeam.name());
    }

}
