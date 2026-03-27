package janggi.view;

import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import janggi.formatter.CampFormatter;
import janggi.util.Parser;
import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER = ",";

    private static final String ELEPHANT_SETTING = """
            %s나라의 상차림을 선택해주세요.
            1. 상마상마
            2. 마상마상
            3. 마상상마
            4. 상마마상""";

    private static final String TURN = "%s나라 차례 입니다.";
    private static final String SOURCE = "공격할 기물의 좌표를 입력해주세요.";
    private static final String DESTINATION = "이동 시킬 목적지 좌표를 입력해주세요.";

    private InputView() {
    }

    public static String readElephantSettingCommand(Camp camp) {
        System.out.println(String.format(ELEPHANT_SETTING, CampFormatter.format(camp)));
        return readLine();
    }

    private static String readLine() {
        String input = SCANNER.nextLine().strip();
        validateInput(input);
        return input;
    }

    private static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }

    public static List<Integer> readSource(Camp camp) {
        System.out.println(String.format(TURN, CampFormatter.format(camp)));
        System.out.println(SOURCE);
        return Parser.parseByDelimiter(DELIMITER, readLine());
    }

    public static List<Integer> readDestination() {
        System.out.println(DESTINATION);
        return Parser.parseByDelimiter(DELIMITER, readLine());
    }
}
