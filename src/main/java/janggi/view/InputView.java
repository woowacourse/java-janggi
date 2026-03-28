package janggi.view;

import janggi.utils.Parser;
import janggi.view.reader.Console;

public final class InputView {

    private static final int MIN_SETUP_COMMAND = 1;
    private static final int MAX_SETUP_COMMAND = 4;

    private InputView() {
    }

    public static int readSetupCommand() {
        int inputCommand = Parser.parseInteger(Console.readLine());
        if (inputCommand >= MIN_SETUP_COMMAND && inputCommand <= MAX_SETUP_COMMAND) {
            return inputCommand;
        }
        throw new IllegalArgumentException(
                "명령 번호는 " + MIN_SETUP_COMMAND + "에서 " + MAX_SETUP_COMMAND + "까지의 정수 값이어야 합니다.");
    }
}
