package view;

import java.util.Scanner;

public class InputView {

    private static final String EMPTY_INPUT = "빈 칸을 입력할 수 없습니다.";
    private static final String NOT_INTEGER = "주어진 범위의 정수만 입력 가능합니다.";

    private static final Scanner SCANNER = new Scanner(System.in);

    public String readPlayerName() {
        String input = SCANNER.nextLine();
        validateEmpty(input);
        return input.trim();
    }

    public int readElephantSetup() {
        String setupNumber = SCANNER.nextLine();

        validateEmpty(setupNumber);

        try {
            return Integer.parseInt(setupNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NOT_INTEGER);
        }
    }


    private void validateEmpty(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_INPUT);
        }
    }
}
