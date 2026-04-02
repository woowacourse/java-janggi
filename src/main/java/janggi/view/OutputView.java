package janggi.view;

import janggi.domain.position.Column;
import janggi.domain.position.Row;
import janggi.dto.BoardDto;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String WARNING_PREFIX = "[WARNING] ";
    private static final int CELL_WIDTH = 3;
    private static final Pattern ANSI_PATTERN = Pattern.compile("\u001B\\[[;\\d]*m");

    public void printBoard(BoardDto boardDto) {
        String[][] board = initBoard();
        for (PieceDto piece : boardDto.pieces()) {
            PositionDto position = piece.position();
            board[position.row()][position.column()] = piece.nameWithColor();
        }

        printCell("");
        for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
            printCell(String.valueOf(column));
        }
        System.out.println();

        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            printCell(String.valueOf(row));
            for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
                printCell(board[row][column]);
            }
            System.out.println();
        }
    }

    private String[][] initBoard() {
        String[][] board = new String[Row.MAX_ROW + 1][Column.MAX_COLUMN + 1];
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; row++) {
            for (int column = Column.MIN_COLUMN; column <= Column.MAX_COLUMN; column++) {
                board[row][column] = "";
            }
        }
        return board;
    }

    private void printCell(String value) {
        System.out.print(value);
        int padding = Math.max(1, CELL_WIDTH - visibleLength(value));
        System.out.print(" ".repeat(padding));
    }

    private int visibleLength(String value) {
        return ANSI_PATTERN.matcher(value).replaceAll("").length();
    }

    public void printCanMovePositions(List<PositionDto> positions) {
        System.out.print("현재 이동 가능한 위치는");

        StringJoiner stringJoiner = new StringJoiner(",");
        for (PositionDto position : positions) {
            stringJoiner.add(" (" + position.row() + "," + position.column() + ")");
        }
        System.out.println(stringJoiner + "입니다.");
        System.out.println();
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
        System.out.println();
    }

    public void printWarningMessage(String errorMessage) {
        System.out.println(WARNING_PREFIX + errorMessage);
        System.out.println();
    }

}
