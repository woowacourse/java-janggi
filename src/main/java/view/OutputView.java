package view;

import java.util.List;
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
    private static final int MIN_ROW_RANGE = 1;
    private static final int MAX_ROW_RANGE = 10;
    private static final int MIN_COL_RANGE = 1;
    private static final int MAX_COL_RANGE = 9;

    public static void printStartMode() {
        System.out.println("1. 시작하기 2. 이어하기");
    }

    public static void printRoomName() {
        System.out.println("게임방의 이름을 입력해주세요.");
    }

    public static void printRoomList(List<String> roomList) {
        System.out.println("게임방 목록입니다. 입장할 방의 이름을 입력해주세요.");
        for (int i = 1; i <= roomList.size(); i++) {
            System.out.printf("%d. %s", i, roomList.get(i-1));
            System.out.println();
        }
    }

    public static void printArrangeCountry(Country country) {
        System.out.printf("%s나라의 진영을 선택해주세요.%n", country.color() + country.title() + Country.RESET);
    }

    public static void printArrangeList(List<String> list, Country country) {
        for (String arrangement : list) {
            System.out.println(country.color() + arrangement + Country.RESET);
        }
    }

    public static void printLine() {
        System.out.println();
    }

    public static void printPositionCountry(Country country) {
        System.out.printf("%s나라의 순서입니다.\n", country.color() + country.title() + Country.RESET);
    }

    public static void printBoard(Board board) {
        System.out.print("   ");
        for (int col = MIN_COL_RANGE; col <= MAX_COL_RANGE; col++) {
            System.out.printf("%d ", col);
        }
        System.out.println();

        for (int row = MIN_ROW_RANGE; row <= MAX_ROW_RANGE; row++) {

            System.out.printf("%2d ", row);

            printColumn(row, board);
            printLine();
        }

        printLine();
    }

    public static void printError(String error) {
        System.out.println(error);
    }

    public static void printWinner(Country country) {
        System.out.printf("%s나라 우승입니다.%n", country.title());
    }

    public static void printScore(Country country, int sumScore) {
        System.out.printf("%s나라 점수: %d%n", country.title(), sumScore);
    }

    private static void printColumn(int row, Board board) {
        for (int col = MIN_COL_RANGE; col <= MAX_COL_RANGE; col++) {
            Position position = Position.of(row, col);

            board.findPiece(position)
                    .ifPresentOrElse(
                            OutputView::printMark,
                            () -> printBoardLine(position)
                    );
        }
    }

    private static void printMark(Piece piece) {
        System.out.printf(String.format("%s ", piece.mark()));
    }

    private static void printBoardLine(Position position) {
        int row = position.row().value();
        int col = position.column().value();
        if (col == MIN_COL_RANGE) {
            System.out.printf(String.format("%-2s", leftLine(row)));
            return;
        }
        if (col == MAX_COL_RANGE) {
            System.out.printf(String.format("%-2s", rightLine(row)));
            return;
        }

        System.out.printf(String.format("%-2s", crossLine(row)));
    }

    private static String leftLine(int row) {
        if (row == MIN_ROW_RANGE) {
            return LEFT_UP_LINE;
        }
        if (row == MAX_ROW_RANGE) {
            return LEFT_DOWN_LINE;
        }
        return LEFT_CROSS_LINE;
    }

    private static String rightLine(int row) {
        if (row == MIN_ROW_RANGE) {
            return RIGHT_UP_LINE;
        }
        if (row == MAX_ROW_RANGE) {
            return RIGHT_DOWN_LINE;
        }
        return RIGHT_CROSS_LINE;
    }

    private static String crossLine(int row) {
        if (row == MIN_ROW_RANGE) {
            return UP_CROSS_LINE;
        }
        if (row == MAX_ROW_RANGE) {
            return DOWN_CROSS_LINE;
        }
        return CENTER_CROSS_LINE;
    }
}
