package team.janggi.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Team;
import team.janggi.util.Parser;

public class ConsoleInputView {
    private static final int Y_COUNT = 9;
    private static final int X_COUNT = 8;
    private static final int SETUP_CHOICE_MIN = 1;

    private static final String TITLE_CHO_FORMATION_SETUP = "초나라 상차림을 선택하세요.";
    private static final String TITLE_HAN_FORMATION_SETUP = "한나라 상차림을 선택하세요.";

    private static final String INVALID_SETUP_CHOICE_MESSAGE =
            SETUP_CHOICE_MIN + "부터 " + JanggiFormation.values().length + "까지의 숫자를 입력하세요.";
    private static final String SELECT_SETUP_CHOICE_MESSAGE =
            "선택 (" + SETUP_CHOICE_MIN + "-" + JanggiFormation.values().length + "): ";

    private static final String PROMPT_MOVE_SOURCE_SUFFIX = "움직일 기물 좌표 (x y) | 저장 시 (save): ";
    private static final String PROMPT_MOVE_DESTINATION_SUFFIX = "도착 좌표 (x y): ";

    private static final String INVALID_COORDINATE_MESSAGE =
            String.format("가로(0~%d), 세로(0~%d) 형식으로 공백을 넣어 입력하세요. (예: 0 6)", X_COUNT, Y_COUNT);


    private final Scanner scanner = new Scanner(System.in);

    public int readChoFormation() {
        printLine(TITLE_CHO_FORMATION_SETUP);
        printLine(INVALID_SETUP_CHOICE_MESSAGE);
        printSetup();
        printText(SELECT_SETUP_CHOICE_MESSAGE);
        return Parser.parseByInteger(scanner.nextLine(), "[ERROR] 상차림 번호는 숫자만 입력해주세요.");
    }

    public int readHanFormation() {
        printLine(TITLE_HAN_FORMATION_SETUP);
        printLine(INVALID_SETUP_CHOICE_MESSAGE);
        printSetup();
        printText(SELECT_SETUP_CHOICE_MESSAGE);
        return Parser.parseByInteger(scanner.nextLine(), "[ERROR] 상차림 번호는 숫자만 입력해주세요.");
    }

    private void printSetup() {
        Arrays.stream(JanggiFormation.values()).forEach(
                setup -> printLine(setup.getNumber() + ". " + setup.getName())
        );
    }

    public String readCommand(Team currentTeam) {
        printText(turnPrefix(currentTeam) + PROMPT_MOVE_SOURCE_SUFFIX);
        return scanner.nextLine();
    }

    public String readGameName() {
        printText("게임을 저장합니다. 게임 이름을 입력해주세요: ");
        return scanner.nextLine();
    }

    public int readStartOption() {
        System.out.println("1. 게임 불러오기");
        System.out.println("2. 새 게임 시작하기");
        System.out.print("시작 옵션 선택: ");
        return Parser.parseByInteger(scanner.nextLine(), "[ERROR] 숫자를 입력해주세요.");
    }

    public int readGameId() {
        System.out.print("불러올 게임 번호를 입력하세요: ");
        return Parser.parseByInteger(scanner.nextLine(), "[ERROR] 숫자를 입력해주세요.");
    }

    public List<Integer> readSourcePosition(Team currentTurn) {
        printText(turnPrefix(currentTurn) + PROMPT_MOVE_SOURCE_SUFFIX);
        return Parser.parseByDelimiter(scanner.nextLine(), "[ERROR] 좌표는 숫자 형식이어야 합니다.");
    }

    public List<Integer> readDestinationPosition(Team currentTurn) {
        printText(turnPrefix(currentTurn) + PROMPT_MOVE_DESTINATION_SUFFIX);
        return Parser.parseByDelimiter(scanner.nextLine(), "[ERROR] 좌표는 숫자 형식이어야 합니다.");
    }

    private String turnPrefix(Team team) {
        return switch (team) {
            case CHO -> "초 차례 — ";
            case HAN -> "한 차례 — ";
            case NONE -> "";
        };
    }

    private void printText(String text) {
        System.out.print(text);
    }

    private void printLine(String text) {
        System.out.println(text);
    }
}