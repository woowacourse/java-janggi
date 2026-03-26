package janggi.view;

import java.util.Scanner;

public class ConsoleReader implements Input {

    private static final String NUMERIC_FORMAT_REGEX = "-?\\d+";

    private final Scanner scanner;

    public ConsoleReader() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int readInteger() {
        String input = scanner.nextLine().trim();
        validateIsBlank(input);
        validateIsNumeric(input);
        return parseToInt(input);
    }

    private void validateIsBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("공백은 허용되지 않습니다.");
        }
    }

    private void validateIsNumeric(String input) {
        if (input.matches(NUMERIC_FORMAT_REGEX)) {
            return;
        }
        throw new IllegalArgumentException("숫자가 아닌 문자를 입력할 수 없습니다.");
    }

    private int parseToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수 범위를 초과할 수 없습니다.");
        }
    }
}
