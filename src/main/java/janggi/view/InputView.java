package janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String HAN_ARRANGEMENT_MESSAGE = "한 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String CHO_ARRANGEMENT_MESSAGE = "초 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String START_POSITION_MESSAGE = "움직일 기물의 시작 좌표를 입력해주세요. (예: 3,4)";
    private static final String END_POSITION_MESSAGE = "움직일 기물의 도착 좌표를 입력해주세요. (예: 4,4)";
    private static final String BASE_DELIMITER = ",";

    private static final String INVALID_POSITION_TYPE = "숫자만 입력 가능합니다.";

    private static final Scanner scanner = new Scanner(System.in);

    public static String askHanArrangement() {
        System.out.println(HAN_ARRANGEMENT_MESSAGE);
        return scanner.nextLine();
    }

    public static String askChoArrangement() {
        System.out.println(CHO_ARRANGEMENT_MESSAGE);
        return scanner.nextLine();
    }

    public static List<Integer> askStartPosition() {
        System.out.println(START_POSITION_MESSAGE);
        String input = scanner.nextLine();
        return Arrays.stream(input.split(BASE_DELIMITER))
                .map(InputView::parseInt)
                .toList();
    }

    public static List<Integer> askEndPosition() {
        System.out.println(END_POSITION_MESSAGE);
        String input = scanner.nextLine();
        return Arrays.stream(input.split(BASE_DELIMITER))
                .map(InputView::parseInt)
                .toList();
    }

    private static Integer parseInt(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_POSITION_TYPE);
        }
    }
}
