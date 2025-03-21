package view;

import java.util.stream.IntStream;

import board.Board;
import board.Position;
import piece.Team;
import piece.Piece;

public class OutputView {

    private static final String red = "\u001B[31m";
    private static final String blue = "\u001B[34m";
    private static final String exit = "\u001B[0m";
    private static final String BLANK_CELL = "ㅁ";

    public void printBoard(final Board board) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        appendHeader(stringBuilder);
        IntStream.range(1, 11).forEach(rowIndex -> appendRow(board, stringBuilder, rowIndex));
        stringBuilder.print();
    }

    private void appendRow(final Board board, final CustomStringBuilder stringBuilder, final int rowIndex) {
        stringBuilder.append(String.valueOf(rowIndex % 10));
        appendBoardByRow(board, stringBuilder, rowIndex);
        stringBuilder.lineSplit();
    }

    private void appendBoardByRow(final Board board, final CustomStringBuilder stringBuilder, final int rowIndex) {
        IntStream.range(1, 10).forEach(columnIndex -> stringBuilder.append(getCellContent(
                board,
                new Position(columnIndex, rowIndex))
        ));
    }

    private String getCellContent(final Board board, final Position position) {
        if (board.isExists(position)) {
            Piece piece = board.findPiece(position);
            return convertContentColor(piece.getTeam(), piece.getDisplayName());
        }
        return BLANK_CELL;
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

}
