package janggi.view;

import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import janggi.util.Parser;
import janggi.view.format.CampFormat;
import janggi.view.format.ElephantSetUpFormat;
import java.util.List;
import java.util.Scanner;

public final class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String DELIMITER = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private InputView() {
    }

    public static ElephantSetUpFormat readElephantSettingCommand(CampType campType) {
        CampFormat campFormat = CampFormat.from(campType);
        System.out.println(LINE_SEPARATOR + "%s나라의 상차림을 선택해주세요.".formatted(campFormat.getName()));
        for (ElephantSetUpFormat elephantSetUpFormat : ElephantSetUpFormat.values()) {
            System.out.println(elephantSetUpFormat.getCommand() + ". " + elephantSetUpFormat.getDescription());
        }
        return ElephantSetUpFormat.findElephantSettingBy(readLine());
    }

    public static List<Integer> readSource(CampType campType) {
        CampFormat campFormat = CampFormat.from(campType);
        System.out.println(LINE_SEPARATOR + "%s나라 차례 입니다.".formatted(campFormat.getName()));
        System.out.println("이동 시킬 기물의 출발 좌표를 행,열 순으로 입력해 주세요. (예: 9,8)");
        return Parser.parseByDelimiter(DELIMITER, readLine());
    }

    public static List<Integer> readDestination() {
        System.out.println(LINE_SEPARATOR + "이동 시킬 기물의 도착 좌표를 행,열 순으로 입력해 주세요. (예: 2,0)");
        return Parser.parseByDelimiter(DELIMITER, readLine());
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
}
