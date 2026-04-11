package team.janggi.view;

import java.util.Scanner;
import team.janggi.domain.BoardSize;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.NormalSetup;

public class ConsoleInputView {
    private static final int SETUP_CHOICE_MIN = 1;
    private static final String TITLE_CHO_NORMAL_SETUP = "초나라 상차림을 선택하세요.";
    private static final String TITLE_HAN_NORMAL_SETUP = "한나라 상차림을 선택하세요.";
    private static final String PROMPT_MOVE_SOURCE_SUFFIX = "움직일 기물 좌표 (열 행): ";
    private static final String PROMPT_MOVE_DESTINATION_SUFFIX = "도착 좌표 (열 행): ";
    private static final String INVALID_COORDINATE_MESSAGE =
            "0~" + (BoardSize.Y - 1) + " 열, 0~" + (BoardSize.X - 1) + " 행 형식으로 다시 입력하세요.";
    private static final String INVALID_SETUP_CHOICE_MESSAGE =
            SETUP_CHOICE_MIN + "부터 " + NormalSetup.values().length + "까지의 숫자를 입력하세요.";

    private final Scanner scanner = new Scanner(System.in);

    public NormalSetup readChoNormalSetup() {
        return readNormalSetup(TITLE_CHO_NORMAL_SETUP);
    }

    public NormalSetup readHanNormalSetup() {
        return readNormalSetup(TITLE_HAN_NORMAL_SETUP);
    }

    public Position readMoveSource(Team currentTurn) {
        return readCoordinate(turnPrefix(currentTurn) + PROMPT_MOVE_SOURCE_SUFFIX);
    }

    public Position readMoveDestination(Team currentTurn) {
        return readCoordinate(turnPrefix(currentTurn) + PROMPT_MOVE_DESTINATION_SUFFIX);
    }

    private String turnPrefix(Team team) {
        if (team == Team.CHO) {
            return "초 차례 — ";
        }
        if (team == Team.HAN) {
            return "한 차례 — ";
        }
        return "";
    }

    private Position readCoordinate(String prompt) {
        while (true) {
            printText(prompt);
            String line = scanner.nextLine().trim();
            Position parsed = tryParsePosition(line);
            if (parsed != null) {
                return parsed;
            }
            printLine(INVALID_COORDINATE_MESSAGE);
        }
    }

    private NormalSetup readNormalSetup(String titleLine) {
        final NormalSetup[] setups = NormalSetup.values();

        NormalSetup readNormalSetup;
        do {
            printLine(titleLine);
            printSetup(setups);

            readNormalSetup = readNormalSetup(setups);
        } while (readNormalSetup == null);

        printLine("");

        return readNormalSetup;
    }

    private NormalSetup readNormalSetup(NormalSetup[] setups) {
        printText("선택 (" + SETUP_CHOICE_MIN + "-" + setups.length + "): ");

        int setupChoice;
        try {
            setupChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            printLine(INVALID_SETUP_CHOICE_MESSAGE);
            return null;
        }

        final boolean isValidateRange = setupChoice >= SETUP_CHOICE_MIN && setupChoice <= setups.length;
        if (!isValidateRange) {
            printLine(INVALID_SETUP_CHOICE_MESSAGE);
            return null;
        }

        return setups[setupChoice - SETUP_CHOICE_MIN];
    }

    private void printSetup(NormalSetup[] setups) {
        for (int i = 0; i < setups.length; i++) {
            printLine((i + SETUP_CHOICE_MIN) + ". " + setups[i].name());
        }
    }

    private Position tryParsePosition(String line) {
        final String[] parts = line.split("\\s+");

        if (parts.length < 2) {
            return null;
        }

        int x;
        int y;
        try {
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        final boolean xOk = x >= 0 && x < BoardSize.X;
        final boolean yOk = y >= 0 && y < BoardSize.Y;

        if (yOk && xOk) {
            return new Position(x, y);
        }

        return null;
    }

    private void printText(String text) {
        System.out.print(text);
    }

    private void printLine(String text) {
        System.out.println(text);
    }

    public long readGameRoomId() {
        printLine("게임 방 번호를 입력하세요.");
        return Long.parseLong(scanner.nextLine().trim());
    }

    public Menu readMenuChoice() {
        printLine("1. 새 게임 시작");
        printLine("2. 이어하기");
        printLine("3. 종료");
        printText("선택 (1-3): ");

        int menuChoice;
        try {
            menuChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            printLine("1 또는 2를 입력하세요.");
            return null;
        }

        final boolean isValidateRange = menuChoice >= 1 && menuChoice <= 3;
        if (!isValidateRange) {
            printLine("1, 2, 3를 입력하세요.");
            return null;
        }

        if (menuChoice == 1) {
            return Menu.CREATE_ROOM;
        }
        if (menuChoice == 2) {
            return Menu.JOIN_ROOM;
        }
        if (menuChoice == 3) {
            return Menu.EXIT;
        }

        throw new IllegalStateException("올바르지 않은 메뉴 번호입니다: " + menuChoice);
    }
}
