package view;

import java.util.Scanner;

public class InputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String INPUT_TABLE_SETTING = "%s의 상차림을 입력하세요. (ex - 상마상마(왼상차림), 마상마상(오른상차림), 마상상마(안상차림), 상마마상(바깥상차림)";
    private static final String INPUT_FROM_POSITION = "움직일 기물의 좌표를 입력하세요.";
    private static final String INPUT_TO_POSITION = "움직이고 싶은 좌표를 입력하세요.";
    private static final String INPUT_CONTINUE_GAME = "진행 중인 게임이 있습니다. 이어하시겠습니까? (yes/no)";

    private final Scanner scanner = new Scanner(System.in);

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

    public String readContinueGame() {
        System.out.println(LINE_SEPARATOR + INPUT_CONTINUE_GAME);
        return scanner.nextLine();
    }
}
