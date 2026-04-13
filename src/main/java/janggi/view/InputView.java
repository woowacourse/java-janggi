package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readPlayerName() {
        String playerName = scanner.nextLine();
        validateNotBlank(playerName);
        return playerName;
    }

    public int readTargetRow() {
        String targetRow = scanner.nextLine();
        validateNotBlank(targetRow);
        return parseToInt(targetRow);
    }

    public int readTargetColumn() {
        String targetColumn = scanner.nextLine();
        validateNotBlank(targetColumn);
        return parseToInt(targetColumn);
    }

    private void validateNotBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 입력값은 공백이 될 수 없습니다.");
        }
    }

    private int parseToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자만 입력 가능합니다.");
        }
    }

    public void close() {
        scanner.close();
    }

    public Boolean readContinueAnswer() {
        String input = scanner.nextLine().trim().toLowerCase();
        validateNotBlank(input);

        if (input.equals("y")) {
            return true;
        }

        if (input.equals("n")) {
            return false;
        }

        throw new IllegalArgumentException("[ERROR] 'y' 또는 'n'만 입력 가능합니다.");
    }
}
