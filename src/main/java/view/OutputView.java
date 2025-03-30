package view;

import static board.Board.BOARD_MAX_HEIGHT;
import static board.Board.BOARD_MAX_WIDTH;
import static board.Board.BOARD_MIN_HEIGHT;
import static board.Board.BOARD_MIN_WIDTH;

import coordinate.Coordinate;
import java.util.Map;
import java.util.Map.Entry;
import piece.Piece;
import team.Country;
import team.Team;

public class OutputView {

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";
    private static final String COLUMN_HEADER = "   １　２　３　４　５　６　７　８　９";

    public void printBoard(Map<Coordinate, Piece> board) {
        println(COLUMN_HEADER);
        for (int y = BOARD_MIN_HEIGHT; y <= BOARD_MAX_HEIGHT; y++) {
            printf("%2d ", y);
            for (int x = BOARD_MIN_WIDTH; x <= BOARD_MAX_WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Piece piece = board.get(coordinate);
                printPiece(piece);
            }
            printNewLine();
        }
        printNewLine();
    }

    private void printPiece(Piece piece) {
        if (piece == null) {
            print(FULL_WIDTH_BAR + FULL_WIDTH_SPACE);
            return;
        }
        print(piece.colorName() + FULL_WIDTH_SPACE);
    }

    public void printScore(Map<Team, Integer> scores) {
        for (Entry<Team, Integer> entry : scores.entrySet()) {
            printf("%s나라: %d점", entry.getKey().getCountry().applyColorCountryName(), entry.getValue());
            printNewLine();
        }
        printNewLine();
    }

    public void printWinner(Country country) {
        printf("%s나라 승!", country.applyColorCountryName());
        printNewLine();
    }

    private void print(String message) {
        System.out.print(message);
    }

    private void println(String message) {
        System.out.println(message);
    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private void printNewLine() {
        System.out.println();
    }

    public void printErrorMessage(String message) {
        printNewLine();
        System.out.println("[ERROR] " + message);
        printNewLine();
    }
}
