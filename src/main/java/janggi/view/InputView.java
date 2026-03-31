package janggi.view;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;
import static janggi.domain.Position.MINIMUM_COLUMN;
import static janggi.domain.Position.MINIMUM_ROW;

import janggi.domain.Position;
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

    public static Position readPosition() {
        String input = Console.readLine();
        String[] parts = input.split(",");
        int inputRow = Parser.parseInteger(parts[0].trim());
        validateRowRange(inputRow);
        int inputColumn = Parser.parseInteger(parts[1].trim());
        validateColumnRange(inputColumn);
        return Position.valueOf(inputRow, inputColumn);
    }

    private static void validateRowRange(final int row) {
        if (row < MINIMUM_ROW || row > MAXIMUM_ROW) {
            throw new IllegalArgumentException("행 입력은 1~10을 입력해야 합니다.");
        }
    }

    private static void validateColumnRange(final int column) {
        if (column < MINIMUM_COLUMN || column > MAXIMUM_COLUMN) {
            throw new IllegalArgumentException("열 입력은 1~9을 입력해야 합니다.");
        }
    }
}
