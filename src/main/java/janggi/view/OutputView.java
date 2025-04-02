package janggi.view;

import janggi.model.Board;
import janggi.model.Color;
import janggi.model.OccupiedPositions;
import janggi.model.PieceIdentity;
import janggi.model.Position;
import java.util.stream.IntStream;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String EXIT = "\u001B[0m";
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
                new Position(rowIndex, columnIndex))
        ));
    }

    private String getCellContent(final Board board, final Position position) {
        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();
        if (occupiedPositions.existPosition(position)) {
            PieceIdentity pieceIdentity = occupiedPositions.getPieceIdentity(position);
            return convertContentColor(pieceIdentity.color(), pieceIdentity.getPieceType().getName());
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

    private String convertContentColor(final Color color, final String content) {
        if (color == Color.BLUE) {
            return String.format("%s%s%s", BLUE, content, EXIT);
        }
        return String.format("%s%s%s", RED, content, EXIT);
    }

    public char toFullWidthCharacter(final String number) {
        char c = number.charAt(0);
        return (char) (c + 0xfee0);
    }

    public void printRedTeamScore(double score) {
        System.out.println(String.format("홍팀 점수: %.1f", score));
    }

    public void printBlueTeamScore(double score) {
        System.out.println(String.format("청팀 점수: %.1f", score));
    }
}
