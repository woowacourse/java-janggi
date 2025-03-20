package view;

import java.util.stream.IntStream;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.piece.Piece;

public class OutputView {

    private static final String red = "\u001B[31m";
    private static final String blue = "\u001B[34m";
    private static final String exit = "\u001B[0m";
    private static final String BLANK_CELL = "ㅁ";

    public void printBoard(Board board) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        appendHeader(stringBuilder);
        IntStream.range(1, 11).forEach(rowIndex -> appendRow(board, stringBuilder, rowIndex));
        stringBuilder.print();
    }

    private void appendRow(Board board, CustomStringBuilder stringBuilder, int rowIndex) {
        stringBuilder.append(String.valueOf(rowIndex % 10));
        appendBoardByRow(board, stringBuilder, rowIndex);
        stringBuilder.lineSplit();
    }

    private void appendBoardByRow(Board board, CustomStringBuilder stringBuilder, int rowIndex) {
        IntStream.range(1, 10).forEach(columnIndex -> {
            stringBuilder.append(getCellContent(board, new Position(
                    columnIndex,
                    rowIndex))
            );
        });
    }

    private String getCellContent(Board board, Position position) {
        if (board.isExists(position)) {
            Piece piece = board.findPiece(position);
            return convertContentColor(piece.getTeam(), piece.getDisplayName());
        }
        return BLANK_CELL;
    }

    private void appendHeader(CustomStringBuilder stringBuilder) {
        stringBuilder.appendBlankCell();
        IntStream.range(1, 10)
                .forEach(columnIndex -> stringBuilder.appendHeader(toFullWidthCharacter(String.valueOf(columnIndex))));
        stringBuilder.lineSplit();
    }

    public void printError(String content) {
        System.out.println(content);
    }

    private String convertContentColor(Team team, String content) {
        if (team == Team.BLUE) {
            return String.format("%s%s%s", blue, content, exit);
        }
        return String.format("%s%s%s", red, content, exit);
    }

    public char toFullWidthCharacter(String number) {
        char c = number.charAt(0);
        return (char) (c + 0xfee0);
    }

}
