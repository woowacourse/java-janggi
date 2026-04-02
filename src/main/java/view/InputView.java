package view;

import java.util.Scanner;

public class InputView {

    private static final String EMPTY_INPUT = "빈 칸을 입력할 수 없습니다.";
    private static final String NOT_INTEGER = "주어진 범위의 정수만 입력 가능합니다.";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String OUT_OF_RANGE = "주어진 범위 내의 숫자를 입력해야 합니다.";

    public String readPlayerName() {
        final String input = SCANNER.nextLine();
        validateEmpty(input);
        return input.trim();
    }

    public int readNumber(final int range) {
        final String input = SCANNER.nextLine();

        validateEmpty(input);

        try {
            final int number = Integer.parseInt(input);
            validateRange(number, range);
            return number;
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(NOT_INTEGER);
        }
    }


    private void validateEmpty(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_INPUT);
        }
    }

    private void validateRange(final int number, final int size) {
        if (number < 1 || number > size) {
            throw new IllegalArgumentException(OUT_OF_RANGE);
        }
    }
}
