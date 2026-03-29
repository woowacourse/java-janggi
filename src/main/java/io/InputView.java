package io;

import domain.setup.Command;
import domain.game.Turn;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Command readCommand() {
        String input = validate(scanner.nextLine().trim());
        return new Command(input);
    }

    private String validate(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 입력은 허용되지 않습니다.");
        }
        return input;
    }
}
