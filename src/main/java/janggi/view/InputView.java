package janggi.view;

import static janggi.Runner.END_TEXT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InputView {
    private static final String HAN_ARRANGEMENT_MESSAGE = "한 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String CHO_ARRANGEMENT_MESSAGE = "초 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String START_POSITION_MESSAGE = "움직일 기물의 시작 좌표를 입력하거나, 종료하려면 '종료'를 입력해주세요. (예: 3,4 또는 종료)";
    private static final String END_POSITION_MESSAGE = "움직일 기물의 도착 좌표를 입력해주세요. (예: 4,4)";
    private static final String BASE_DELIMITER = ",";
    private static final String CONSENT_END_MESSAGE = "종료하는 데 동의하시면 '종료'를, 계속하시려면 아무 키나 입력해주세요.";

    private static final String INVALID_POSITION_TYPE = "숫자만 입력 가능합니다.";

    private static final Scanner scanner = new Scanner(System.in);

    public static Optional<Integer> askLoadGame() {
        System.out.println("불러오려는 게임의 번호를, 또는 새로 생성하려면 '*'를 입력해주세요.");
        String input = scanner.nextLine();
        System.out.println();
        if(input.trim().equals("*")) {
            return Optional.empty();
        }
        return Optional.of(parseInt(input));
    }

    public static String askGameName() {
        System.out.println("생성하려는 게임의 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public static String askHanArrangement() {
        System.out.println(HAN_ARRANGEMENT_MESSAGE);
        return scanner.nextLine();
    }

    public static String askChoArrangement() {
        System.out.println(CHO_ARRANGEMENT_MESSAGE);
        return scanner.nextLine();
    }

    public static Optional<List<Integer>> askStartPosition() {
        System.out.println(START_POSITION_MESSAGE);
        String input = scanner.nextLine();

        if(input.equals(END_TEXT)) {
            return Optional.empty();
        }

        return Optional.of(Arrays.stream(input.split(BASE_DELIMITER))
                .map(InputView::parseInt)
                .toList());
    }

    public static String consentEnd() {
        System.out.println(CONSENT_END_MESSAGE);
        return scanner.nextLine();
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
