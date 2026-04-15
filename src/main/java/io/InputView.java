package io;

import java.util.Scanner;

public class InputView {
    private static final String YES = "y";
    private static final String NO = "n";
    private final Scanner scanner = new Scanner(System.in);

    public String readRawCommand() {
        return validate(scanner.nextLine().trim());
    }

    public boolean readRestoreAnswer() {
        String input = validate(scanner.nextLine().trim()).toLowerCase();
        if (YES.equals(input)) {
            return true;
        }
        if (NO.equals(input)) {
            return false;
        }
        throw new IllegalArgumentException("[ERROR] y 또는 n만 입력할 수 있습니다.");
    }

    private String validate(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 입력은 허용되지 않습니다.");
        }
        return input;
    }
}
