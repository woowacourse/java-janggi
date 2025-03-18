package janggi.view;

import janggi.domain.Board;
import java.util.StringJoiner;

public class Viewer {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String BLANK = "";

    public void printBoard(Board board) {
        StringJoiner enterJoiner = new StringJoiner(LINE_SEPARATOR).add(formatFirstRowOfBoard());
        for (int row = 1; row <= 10; row++) {
            StringJoiner lineJoiner = new StringJoiner(BLANK).add(String.valueOf(row));
            for (int column = 1; column <= 9; column++) {
                lineJoiner.add(board.getPieceName(row, column));
            }
            enterJoiner.add(lineJoiner.toString());
        }

        System.out.println(enterJoiner);
    }

    private String formatFirstRowOfBoard() {
        StringJoiner joiner = new StringJoiner(BLANK).add(" ");

        for (int i = 1; i <= 9; i++) {
            joiner.add(String.valueOf(i));
        }

        return joiner.toString();
    }
}
