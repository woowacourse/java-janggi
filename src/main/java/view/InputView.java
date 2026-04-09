package view;

import board.SangSetupType;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import position.Column;
import position.Position;
import position.Row;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Pattern POSITION_PATTERN =
        Pattern.compile("(?<row>\\d+)\\s*,\\s*(?<column>\\d+)");

    public SangSetupType readSangSetup() {
        final int inputNumber = parseInt(readStrippedLine());
        return SangSetupInput.from(inputNumber);
    }

    public Position readPosition() {
        return parsePosition(readStrippedLine());
    }

    public Long readLong() {
        return parseLong(readStrippedLine());
    }

    public boolean readYesOrNo() {
        final String input = readStrippedLine().toLowerCase();
        if (input.equals("y")) {
            return true;
        }
        if (input.equals("n")) {
            return false;
        }
        throw new IllegalArgumentException("y 또는 n 만 입력할 수 있습니다.");
    }

    public ServiceMenu askServiceMenu() {
        int menuNumber = parseInt(readStrippedLine());
        return ServiceMenu.from(menuNumber);
    }

    private Long parseLong(final String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }

    private int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다. 다시 입력해주세요.");
        }
    }

    private Position parsePosition(final String input) {
        final Matcher matcher = POSITION_PATTERN.matcher(input);
        validatePositionFormat(matcher);

        final int row = parseInt(matcher.group("row"));
        final int column = parseInt(matcher.group("column"));
        try {
            return new Position(new Row(row), new Column(column));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("좌표의 범위가 유효하지 않습니다. 다시 입력해주세요.");
        }
    }

    private void validatePositionFormat(Matcher matcher) {
        if (!matcher.matches()) {
            throw new IllegalArgumentException("좌표는 1,2 형식으로 입력해야 합니다. 다시 입력해주세요.");
        }
    }

    private String readStrippedLine() {
        final String input = SCANNER.next();
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다. 다시 입력해주세요.");
        }
        return input.strip();
    }
}