package janggi.view;

import java.util.Scanner;

public final class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER = ",";
    private static final String NEW_LINE = System.lineSeparator();

    private static final String HAN = "한";
    private static final String CHO = "초";

    private static final String ELEPHANT_SETTING = """
            
            %s나라의 상차림을 선택해주세요.
            1. 상마상마
            2. 마상마상
            3. 마상상마
            4. 상마마상""";

    private InputView() {
    }

    public static String readHanElephantSettingCommand() {
        System.out.println(String.format(ELEPHANT_SETTING, HAN));
        return readLine();
    }

    public static String readChoElephantSettingCommand() {
        System.out.println(String.format(ELEPHANT_SETTING, CHO));
        return readLine();
    }

    private static String readLine() {
        String input = SCANNER.nextLine().strip();
        validateInput(input);
        return input;
    }

    private static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] qls");
        }
    }


}
