package janggi.view;

import janggi.Board;
import janggi.Movable;
import janggi.Team;
import janggi.point.Point;
import java.util.Arrays;
import java.util.List;

public class BoardView {

    public static final String EXIT_COLOR_CODE = "\u001B[0m" ;

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private final String[][] matrix;

    public BoardView() {
        matrix = new String[ROW_SIZE][COLUMN_SIZE];
        clearBoard();
    }

    public void displayBoard(Board board) {
        clearBoard();
        List<Movable> pieces = board.getRunningPieces();
        placePieces(pieces);
        for (int row = 0; row < ROW_SIZE; row++) {
            String line = String.format(" %2s |",  "\u001B[37m" + toFullWidthNumber(row) + "\u001B[0m");
            for (String token : matrix[row]) {
                line += String.format(" %2s |", token);
            }
            System.out.println(line);
        }

        String line = String.format(" %2s |", " ");
        for (int column = 0; column < COLUMN_SIZE; column++) {
            line += String.format(" %2s |", "\u001B[37m" + toFullWidthNumber(column) + "\u001B[0m");
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
                sb.append((char) (c - '0' + '\uFF10')); // 전각 문자로 변환
            } else {
                sb.append(c); // 숫자가 아니면 그대로
            }
        }
        return sb.toString();
    }

    private void clearBoard() {
        Arrays.stream(matrix).forEach(
                row -> Arrays.fill(row, "\u001B[30m" + "ㅁ" + "\u001B[0m")
        );
    }

    private void placePieces(List<Movable> pieces) {
        for(Movable piece : pieces) {
            Point point = piece.getPoint();
            Team team = piece.getTeam();
            matrix[point.row()][point.column()] = team.getColorCode() + piece.getName() + EXIT_COLOR_CODE;
        }
    }
}
