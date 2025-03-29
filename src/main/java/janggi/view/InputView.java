package janggi.view;

import java.util.Scanner;
import java.util.function.Supplier;

public class InputView {

    public static final String SETUP_MENU_REGEX = "[1-4]";
    public static final String MOVE_COMMAND_REGEX = "move [0-8][0-9] [0-8][0-9]";
    public static final String END_COMMAND_REGEX = "end";

    private final Scanner scanner = new Scanner(System.in);

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
