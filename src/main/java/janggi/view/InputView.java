package janggi.view;

import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final String HAN_ARRANGEMENT_MESSAGE = "한 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String CHO_ARRANGEMENT_MESSAGE = "초 진영의 배치를 입력해주세요. (예: 마상마상, 마상상마, 상마상마, 상마마상)";
    private static final String START_POSITION_MESSAGE = "움직일 기물의 시작 좌표를 입력해주세요. (예: 3,4)";
    private static final String END_POSITION_MESSAGE = "움직일 기물의 도착 좌표를 입력해주세요. (예: 4,4)";
    private static final String ROOM_ID_MESSAGE = "입장할 게임 방을 알려주세요. 새 게임을 만들려면 -1을 입력하세요. : ";
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
        return PositionParser.parsePositionInput(input);
    }

    public static List<Integer> askEndPosition() {
        System.out.println(END_POSITION_MESSAGE);
        String input = scanner.nextLine();
        return PositionParser.parsePositionInput(input);
    }

    public static long askRoomId() {
        System.out.println(ROOM_ID_MESSAGE);
        String input = scanner.nextLine();
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능");
        }
    }
}
