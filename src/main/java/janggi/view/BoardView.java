package janggi.view;

import janggi.game.Board;
import janggi.piece.Piece;
import janggi.game.Team;
import janggi.point.Point;
import java.util.Arrays;
import java.util.List;

public class BoardView {

    private static final String EXIT_COLOR_CODE = "\u001B[0m";
    private static final String BLACK_CODE = "\u001B[30m";
    private static final String WHITE_CODE = "\u001B[37m";
    private static final String EXPRESSION = " %2s |";

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private final String[][] matrix;

    public BoardView() {
        matrix = new String[ROW_SIZE][COLUMN_SIZE];
        clearBoard();
    }

    public void displayBoard(Board board) {
        clearBoard();
        List<Piece> pieces = board.getRunningPieces();
        placePieces(pieces);
        for (int row = 0; row < ROW_SIZE; row++) {
            String line = String.format(EXPRESSION, WHITE_CODE + toFullWidthNumber(row) + EXIT_COLOR_CODE);
            for (String token : matrix[row]) {
                line += String.format(EXPRESSION, token);
            }
            System.out.println(line);
        }

        StringBuilder line = new StringBuilder(String.format(EXPRESSION, " "));
        for (int column = 0; column < COLUMN_SIZE; column++) {
            line.append(String.format(EXPRESSION, WHITE_CODE + toFullWidthNumber(column) + EXIT_COLOR_CODE));
        }
        System.out.println(line);
    }

    public void printTeam(Team team) {
        System.out.printf("%s의 차례입니다.%n", team.getText());
    }

    private String toFullWidthNumber(int number) {
        String value = String.valueOf(number);
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append((char) (c - '0' + '\uFF10'));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void clearBoard() {
        Arrays.stream(matrix).forEach(
                row -> Arrays.fill(row, BLACK_CODE + "ㅁ" + EXIT_COLOR_CODE)
        );
    }

    private void placePieces(List<Piece> pieces) {
        for (Piece piece : pieces) {
            Point point = piece.getPoint();
            Team team = piece.getTeam();
            matrix[point.row()][point.column()] = team.getColorCode() + piece.getName() + EXIT_COLOR_CODE;
        }
    }
}
