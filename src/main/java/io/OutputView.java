package io;

import domain.board.Board;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.game.GameResult;
import domain.game.Turn;

public class OutputView {
    private static final String REQUEST_SETUP = """
            [%s 진영] 배치를 선택하세요.
            1. 마-상-마-상 (Horse-Elephant-Horse-Elephant)
            2. 마-상-상-마 (Horse-Elephant-Elephant-Horse)
            3. 상-마-마-상 (Elephant-Horse-Horse-Elephant)
            4. 상-마-상-마 (Elephant-Horse-Elephant-Horse)""";
    private static final String REQUEST_MOVE = "[%s 진영] {출발 좌표} {도착 좌표} 형식으로 입력해 수를 두세요. (ex. e6 e5)";
    private final ConsoleFormatter formatter = new ConsoleFormatter();

    public void printSetupTable(Turn turn) {
        String message = String.format(REQUEST_SETUP, formatter.formatTurn(turn.getTeam()));
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printBoard(Board board, Turn turn) {
        StringBuilder stringBuilder = new StringBuilder();
        appendHeader(stringBuilder, turn);
        appendRows(stringBuilder, board);
        System.out.println(stringBuilder);
    }

    private void appendHeader(StringBuilder stringBuilder, Turn turn) {
        stringBuilder.append("--------------------------------------\n");
        stringBuilder.append("현재 턴: [").append(formatter.formatColoredTurn(turn))
                .append(" 진영]\n\n");
        stringBuilder.append("     a   b   c   d   e   f   g   h   i\n");
    }

    private void appendRows(StringBuilder stringBuilder, Board board) {
        for (Row row : Row.values()) {
            appendRow(stringBuilder, board, row);
        }
    }

    private void appendRow(StringBuilder stringBuilder, Board board, Row row) {
        stringBuilder.append(String.format("%2s  ", formatter.formatRow(row)));
        for (Column column : Column.values()) {
            appendCell(stringBuilder, board, new Position(column, row));
        }
        stringBuilder.append("\n");
    }

    private void appendCell(StringBuilder stringBuilder, Board board, Position position) {
        stringBuilder.append(cellDisplay(board, position)).append(" ");
    }

    private String cellDisplay(Board board, Position position) {
        return board.findPieceByPosition(position)
                .map(formatter::formatPiece)
                .orElse("...");
    }

    public void printPieceMovement(Turn turn) {
        System.out.printf((REQUEST_MOVE) + "%n", formatter.formatTurn(turn.getTeam()));
    }

    public void printGameResult(GameResult gameResult) {
        System.out.println(formatter.formatGameResult(gameResult));
    }
}
