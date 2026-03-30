package view;

import board.SangSetup;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public SangSetup readSangSetup() {
        String input = readStrippedLine();
        try {
            int inputNumber = Integer.parseInt(input);
            return SangSetupType.from(inputNumber);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
        }
    }

    private String readStrippedLine() {
        String input = SCANNER.next();
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다.");
        }
        return input.strip();
    }

}
