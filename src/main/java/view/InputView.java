package view;

import domain.board.Position;
import domain.board.SetUp;
import domain.game.GameCommand;
import domain.game.MoveCommand;
import domain.game.PassCommand;

import java.util.Scanner;

public class InputView {
    private static final String PASS_COMMAND = "pass";
    private static final String INVALID_COMMAND_ERROR_MESSAGE = "[ERROR] 명령은 pass 또는 x y x y 형식이어야 합니다.";
    private static final String INPUT_CLOSED_ERROR_MESSAGE = "[ERROR] 입력이 종료되었습니다.";
    private static final int MOVE_COMMAND_TOKEN_SIZE = 4;
    private static final int DISPLAYED_LAST_ROW = 0;
    private static final int ACTUAL_LAST_ROW = 10;
    private static final String COMMAND_MUST_BE_NUMBER_ERROR_MESSAGE = "[ERROR] 상차림 번호는 숫자여야 합니다.";
    private static final String INVALID_MENU_ERROR_MESSAGE = "[ERROR] 메뉴는 1 또는 2만 입력 가능합니다.";
    private static final String INVALID_GAME_ID_ERROR_MESSAGE = "[ERROR] 게임 번호는 숫자여야 합니다.";

    private final Scanner scanner = new Scanner(System.in);

    public int readSetUp() {
        String input = readLine();

        try {
            int setUpNumber = Integer.parseInt(input);
            SetUp.from(setUpNumber);
            return setUpNumber;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(COMMAND_MUST_BE_NUMBER_ERROR_MESSAGE);
        }
    }

    public GameCommand readCommand() {
        String input = readLine();

        if (PASS_COMMAND.equalsIgnoreCase(input)) {
            return new PassCommand();
        }

        String[] tokens = input.split(" ");

        if (tokens.length != MOVE_COMMAND_TOKEN_SIZE) {
            throw new IllegalArgumentException(INVALID_COMMAND_ERROR_MESSAGE);
        }

        return new MoveCommand(
                parsePosition(tokens[0], tokens[1]),
                parsePosition(tokens[2], tokens[3])
        );
    }

    public String readMainMenu() {
        String input = readLine();

        if (!"1".equals(input) && !"2".equals(input)) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR_MESSAGE);
        }

        return input;
    }

    public int readGameId() {
        String input = readLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_GAME_ID_ERROR_MESSAGE);
        }
    }

    private Position parsePosition(String xToken, String yToken) {
        try {
            int x = Integer.parseInt(xToken);
            int y = Integer.parseInt(yToken);

            if (y == DISPLAYED_LAST_ROW) {
                y = ACTUAL_LAST_ROW;
            }

            return new Position(x, y);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(INVALID_COMMAND_ERROR_MESSAGE);
        }
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException(INPUT_CLOSED_ERROR_MESSAGE);
        }

        return scanner.nextLine().trim();
    }
}
