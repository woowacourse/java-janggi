package view;

import static object.board.Board.BOARD_MAX_HEIGHT;
import static object.board.Board.BOARD_MAX_WIDTH;
import static object.board.Board.BOARD_MIN_HEIGHT;
import static object.board.Board.BOARD_MIN_WIDTH;

import java.util.Map;
import java.util.Map.Entry;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.team.Country;

public class OutputView {

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";
    private static final String COLUMN_HEADER = "   １　２　３　４　５　６　７　８　９";

    public void printGameStartMessage() {
        println("새 게임을 시작합니다.");
        printNewLine();
    }

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

    public void printScore(Map<Country, Integer> scores) {
        for (Entry<Country, Integer> entry : scores.entrySet()) {
            printf("%s나라: %d점", entry.getKey().applyColorCountryName(), entry.getValue());
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
}
