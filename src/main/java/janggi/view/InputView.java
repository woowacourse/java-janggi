package janggi.view;

import janggi.domain.piece.Camp;
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
    private static final String START_POSITION = "공격할 기물의 좌표를 입력해주세요.";
    private static final String GOAL_POSITION = "이동 시킬 목적지 좌표를 입력해주세요.";

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
            throw new IllegalArgumentException("[ERROR] 잘못된 입력 형식입니다.");
        }
    }

    public static List<Integer> readStartPosition(Camp camp) {
        System.out.println(String.format(TURN, CampFormatter.format(camp)));
        System.out.println(START_POSITION);
        return Parser.parseByDelimiter(DELIMITER, readLine());
    }

    public static List<Integer> readGoalPosition() {
        System.out.println(GOAL_POSITION);
        return Parser.parseByDelimiter(DELIMITER, readLine());
    }
}
