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
        Arrays.stream(matrix).forEach(
                row -> Arrays.fill(row, " ")
        );
    }

    public void displayBoard(Board board) {
        List<Movable> pieces = board.getRunningPieces();
        placePieces(pieces);
        for (int row = 0; row < ROW_SIZE; row++) {
            System.out.printf("%2d | %s |%n", row, String.join(" | ", matrix[row]));
        }

        String line = String.format("%2s |", " ");
        for (int column = 0; column < COLUMN_SIZE; column++) {
            line += String.format("%2d |", column);
        }
        System.out.println(line);
    }

    private void placePieces(List<Movable> pieces) {
        for(Movable piece : pieces) {
            Point point = piece.getPoint();
            Team team = piece.getTeam();
            matrix[point.row()][point.column()] = team.getColorCode() + piece.getName() + EXIT_COLOR_CODE;
        }
    }

    public void printTeam(Team team) {
        System.out.printf("%s의 차례입니다.%n", team.getText());
    }
}
