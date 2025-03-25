package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int BOARD_HEIGHT = 10;
    private static final int BOARD_WIDTH = 10;

    public static String[] inputPositionsWithBlank() {
        String string = scanner.nextLine();
        validateWithBlank(string);
        String[] positions = string.split(" ");
        validatePositionsRange(positions);
        return positions;
    }

    private static void validateWithBlank(String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("입력값이 없습니다. 올바른 좌표를 입력해주세요.");
        }
        if (string.split(" ").length != 2) {
            throw new IllegalArgumentException("올바른 좌표를 입력해주세요.");
        }
    }

    private static void validatePositionsRange(String[] positions) {
        for (String positionStr : positions) {
            if (positionStr.length() != 2 || !Character.isDigit(positionStr.charAt(0)) || !Character.isDigit(
                    positionStr.charAt(1))) {
                throw new IllegalArgumentException("좌표는 두 자리 숫자로 입력해야 합니다.");
            }
            int file = Character.getNumericValue(positionStr.charAt(0));
            int rank = Character.getNumericValue(positionStr.charAt(1));

            if (file < 0 || file >= BOARD_HEIGHT || rank < 1 || rank >= BOARD_WIDTH) {
                throw new IllegalArgumentException("좌표가 장기판 범위를 벗어났습니다.");
            }
        }
    }
}
