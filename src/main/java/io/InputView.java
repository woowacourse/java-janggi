package io;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readRawCommand() {
        return validate(scanner.nextLine().trim());
    }

    private String validate(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 입력은 허용되지 않습니다.");
        }
        return input;
    }
}
