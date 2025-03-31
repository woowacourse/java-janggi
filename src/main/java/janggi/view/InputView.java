package janggi.view;

import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class InputView {

    private static final String SETUP_MENU_REGEX = "[1-4]";
    private static final String MOVE_COMMAND_REGEX = "move [0-8][0-9] [0-8][0-9]";
    private static final String END_COMMAND_REGEX = "end";
    private static final String INTEGER_REGEX = "\\d*";
    private static final int START_GAME = 0;

    private static final Scanner scanner = new Scanner(System.in);

    public String readGameId(final List<Integer> storedGameIds) {
        return retryUtilSuccess(() -> {
            String input = scanner.nextLine();
            if (!input.matches(INTEGER_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
            }
            int id = Integer.parseInt(input);
            if (id != START_GAME && !storedGameIds.contains(id)) {
                throw new IllegalArgumentException("[ERROR] 목록에 있는 숫자를 입력해주세요.");
            }
            return input;
        });
    }

    public String readSetupOption() {
        return retryUtilSuccess(() -> {
            String input = scanner.nextLine();
            if (!input.matches(SETUP_MENU_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 1~4의 숫자만 입력할 수 있습니다.");
            }
            return input;
        });
    }

    public String readCommand() {
        return retryUtilSuccess(() -> {
            String input = scanner.nextLine();
            if (!input.matches(MOVE_COMMAND_REGEX) && !input.matches(END_COMMAND_REGEX)) {
                throw new IllegalArgumentException("[ERROR] 올바른 형식으로 입력해주세요.");
            }
            return input;
        });
    }

    private String retryUtilSuccess(final Supplier<String> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
