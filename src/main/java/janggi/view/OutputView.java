package janggi.view;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Team;
import java.util.Map;

public class OutputView {
    private static final String ERROR_PREFIX = "[ERROR]: ";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";   // 한(HAN)
    private static final String ANSI_BLUE = "\u001B[34m";  // 초(CHO)
    private static final String ANSI_WHITE = "\u001B[37m"; // 빈 공간
    private static final String FULL_WIDTH_SPACE = "\u3000"; // 전각 공백
    private static final String FULL_WIDTH_DOT = "．"; // 전각 마침표 (．)
    private static final String[] FULL_WIDTH_NUMBERS = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"}; // 전각 숫자 배열

    public static void printBoard(Map<Position, Space> capturedBoard) {
        System.out.println("    " + "-".repeat(27));
        for (int y = 9; y >= 0; y--) {
            System.out.print(FULL_WIDTH_NUMBERS[y] + FULL_WIDTH_SPACE + "|");
            for (int x = 0; x <= 8; x++) {
                Space space = capturedBoard.get(new Position(x, y));
                System.out.print(formatSpace(space));
            }
            System.out.println(FULL_WIDTH_SPACE + "|");
        }
        System.out.println("    " + "-".repeat(27));
        printXCoordinates();
    }

    public static void printErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    private static String formatSpace(Space space) {
        if (space.isBlank()) { //
            return ANSI_WHITE + FULL_WIDTH_SPACE + FULL_WIDTH_DOT + ANSI_RESET;
        }

        Piece piece = (Piece) space; //
        String color = ANSI_BLUE;
        if(piece.isEqualTeam(Team.HAN))  {
            color = ANSI_RED;
        }
        return color + FULL_WIDTH_SPACE + piece.toString() + ANSI_RESET; //
    }

    private static void printXCoordinates() {
        System.out.print(" " + FULL_WIDTH_SPACE.repeat(3));
        for (int x = 0; x <= 8; x++) {
            System.out.print(FULL_WIDTH_NUMBERS[x] + FULL_WIDTH_SPACE);
        }
        System.out.println();
    }
}
