package view;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.position.Position;

public class OutputView {
    private static final String LEFT_CROSS_LINE = "├";
    private static final String UP_CROSS_LINE = "┬";
    private static final String RIGHT_CROSS_LINE = "┤";
    private static final String DOWN_CROSS_LINE = "┴";
    private static final String CENTER_CROSS_LINE = "┼";
    private static final String LEFT_UP_LINE = "┌";
    private static final String LEFT_DOWN_LINE = "└";
    private static final String RIGHT_UP_LINE = "┐";
    private static final String RIGHT_DOWN_LINE = "┘";

    public static void printArrangeCountry(Country country) {
        System.out.printf("%s나라의 진영을 선택해주세요.%n", country.color() + country.title() + Country.RESET);
    }

    public static void printLine() {
        System.out.println();
    }

    public static void printPositionCountry(Country country) {
        System.out.printf("%s나라의 순서입니다.\n", country.color() + country.title() + Country.RESET);
    }

    public static void printBoard(Board board) {
        System.out.print("   ");
        for (int col = 1; col <= 9; col++) {
            System.out.printf("%d ", col);
        }
        System.out.println();

        for (int row = 1; row <= 10; row++) {

            System.out.printf("%2d ", row);

            printColumn(row, board);
            printLine();
        }

        printLine();
    }

    public static void printWinner(Country winner) {
        System.out.println(winner.title() + "나라가 승리했습니다.");
    }

    public static void printScore(Country country, double score) {
        System.out.println(country.title() + "나라 점수: " + score);
    }

    public static void printLoadedGameMessage(){
        System.out.println("저장된 게임을 불러와 이어서 시작합니다.");
    }

    public static void printNewGameMessage(){
        System.out.println("게임을 시작합니다.");
    }

    private static void printColumn(int row, Board board) {
        for (int col = 1; col <= 9; col++) {
            Position position = Position.of(row, col);
            Piece piece = board.findPiece(position);
            if (piece == null) {
                printBoardLine(row, col);
                continue;
            }
            printMark(piece);
        }
    }

    private static void printMark(Piece piece) {
        System.out.printf(String.format("%s ", piece.mark()));
    }

    private static void printBoardLine(int row, int col) {
        if (col == 1) {
            System.out.printf(String.format("%-2s", leftLine(row)));
            return;
        }
        if (col == 9) {
            System.out.printf(String.format("%-2s", rightLine(row)));
            return;
        }

        System.out.printf(String.format("%-2s", crossLine(row)));
    }

    private static String leftLine(int row) {
        if (row == 1) {
            return LEFT_UP_LINE;
        }
        if (row == 10) {
            return LEFT_DOWN_LINE;
        }
        return LEFT_CROSS_LINE;
    }

    private static String rightLine(int row) {
        if (row == 1) {
            return RIGHT_UP_LINE;
        }
        if (row == 10) {
            return RIGHT_DOWN_LINE;
        }
        return RIGHT_CROSS_LINE;
    }

    private static String crossLine(int row) {
        if (row == 1) {
            return UP_CROSS_LINE;
        }
        if (row == 10) {
            return DOWN_CROSS_LINE;
        }
        return CENTER_CROSS_LINE;
    }
}
