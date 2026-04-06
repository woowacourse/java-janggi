package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    private String readLine() {
        return scanner.nextLine();
    }

    public String readPlayerName() {
        return readNotBlankLine();
    }

    public int readPosition() {
        return readInteger();
    }

    private String readNotBlankLine() {
        String line = readLine();
        if (line.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 공백이 아닌 유효한 값을 입력하세요.");
        }
        return line;
    }

    private int readInteger() {
        try {
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다.");
        }
    }

    public String readUserCommand() {
        return readNotBlankLine();
    }

    public void close() {
        scanner.close();
    }

    public long readGameId() {
        return readInteger();
    }
}
