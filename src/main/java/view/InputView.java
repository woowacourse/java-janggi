package view;

import java.util.Scanner;

public class InputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String INPUT_LOAD_OR_CREATE = "보드를 불러오시겠습니까? 예: 1, 아니오: 2";
    private static final String INPUT_BOARD_SELECT = "불러올 보드를 선택해 주세요.";
    private static final String INPUT_TABLE_SETTING = "%s의 상차림을 입력하세요. (ex - 상마상마, 마상마상, 마상상마, 상마마상)";
    private static final String INPUT_FROM_POSITION = "움직일 기물의 좌표를 입력하세요.";
    private static final String INPUT_TO_POSITION = "움직이고 싶은 좌표를 입력하세요.";

    private final Scanner scanner = new Scanner(System.in);

    public String readLoadOrCreateBoard() {
        System.out.println(INPUT_LOAD_OR_CREATE);
        return scanner.nextLine();
    }

    public String readBoardSelect() {
        System.out.println(INPUT_BOARD_SELECT);
        return scanner.nextLine();
    }

    public String readTableSetting(String countryName) {
        System.out.printf(LINE_SEPARATOR + INPUT_TABLE_SETTING + LINE_SEPARATOR, countryName);
        return scanner.nextLine();
    }

    public String readFromPosition() {
        System.out.println(LINE_SEPARATOR + INPUT_FROM_POSITION);
        return scanner.nextLine();
    }

    public String readToPosition() {
        System.out.println(LINE_SEPARATOR + INPUT_TO_POSITION);
        return scanner.nextLine();
    }
}
