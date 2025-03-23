package janggi.view;

import janggi.game.Board;
import janggi.game.Team;
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.Arrays;
import java.util.Map;

public class BoardView {

    public static final String EXIT_COLOR_CODE = "\u001B[0m";
    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;
    private static final String EMPTY_CELL = "\u001B[30mㅁ\u001B[0m";

    private final String[][] matrix = new String[ROW_SIZE][COLUMN_SIZE];

    public BoardView() {
        clearBoard();
    }

    public void printTeam(Team team) {
        System.out.printf("%s의 차례입니다.%n", team.getText());
    }

    public void printSelectedPiece(Movable piece) {
        System.out.printf("%s를(을) 선택했습니다.%n", piece.getName());
    }

    public void printMovingResult(Point startPoint, Point targetPoint) {
        System.out.printf("(%d, %d) -> (%d, %d)로 이동했습니다.%n",
            startPoint.row(), startPoint.column(),
            targetPoint.row(), targetPoint.column());
    }

    public void displayBoard(Board board) {
        clearBoard();
        placePieces(board.getRunningPieces());

        for (int row = 0; row < ROW_SIZE; row++) {
            System.out.println(buildRow(row));
        }
        System.out.println(buildColumnHeaders());
    }

    private String buildRow(int row) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(
            String.format(" %2s |", "\u001B[37m" + toFullWidthNumber(row) + "\u001B[0m"));
        for (String token : matrix[row]) {
            rowBuilder.append(String.format(" %2s ", token));
        }
        return rowBuilder.toString();
    }

    private String buildColumnHeaders() {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(" ㅁ |");
        for (int column = 0; column < COLUMN_SIZE; column++) {
            headerBuilder.append(
                String.format(" %2s ", "\u001B[37m" + toFullWidthNumber(column) + "\u001B[0m"));
        }
        return headerBuilder.toString();
    }

    private void clearBoard() {
        for (String[] row : matrix) {
            Arrays.fill(row, EMPTY_CELL);
        }
    }

    private void placePieces(Map<Point, Movable> pieces) {
        for (Map.Entry<Point, Movable> entry : pieces.entrySet()) {
            Point point = entry.getKey();
            Movable piece = entry.getValue();
            matrix[point.row()][point.column()] = formatPiece(piece);
        }
    }

    private String formatPiece(Movable piece) {
        return piece.getTeam().getColorCode() + piece.getName() + EXIT_COLOR_CODE;
    }

    private String toFullWidthNumber(int number) {
        String numberStr = String.valueOf(number);
        StringBuilder sb = new StringBuilder();
        for (char c : numberStr.toCharArray()) {
            sb.append(Character.isDigit(c) ? (char) (c - '0' + '\uFF10') : c);
        }
        return sb.toString();
    }
}
