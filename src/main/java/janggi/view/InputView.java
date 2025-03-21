package janggi.view;

import janggi.domain.Position;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        System.out.println("명령어를 입력하세요: 이동=move, 종료=end");
        String input = scanner.nextLine();
        validateEmptyInput(input);
        return input.strip();
    }

    private static void validateEmptyInput(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 입력입니다.");
        }
    }

    public Position readStartPosition() {
        System.out.println("움직일 기물을 선택하세요(행 열)");
        return readPosition();
    }

    public Position readMovePosition() {
        System.out.println("이동할 칸을 선택하세요(행 열)");
        return readPosition();
    }

    private Position readPosition() {
        try {
            String input = scanner.nextLine();
            validateEmptyInput(input);
            String[] splitInput = input.split(" ");
            int row = Integer.parseInt(splitInput[0]);
            int column = Integer.parseInt(splitInput[1]);
            return Position.of(row, column);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
