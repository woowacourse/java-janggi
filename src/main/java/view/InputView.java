package view;

import domain.board.Position;
import java.util.Scanner;

public class InputView {
    private static final String PASS_COMMAND = "pass";
    private static final int MOVE_COMMAND_TOKEN_SIZE = 4;
    private static final int DISPLAYED_LAST_ROW = 0;
    private static final int ACTUAL_LAST_ROW = 10;
    private static final String YES = "y";
    private static final String NO = "n";

    private final Scanner scanner = new Scanner(System.in);

    public int readSetUpNumber() {
        String input = readLine();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 상차림 번호는 숫자여야 합니다.");
        }
    }

    public GameCommand readCommand() {
        String input = readLine();

        if (PASS_COMMAND.equalsIgnoreCase(input)) {
            return new PassCommand();
        }

        String[] tokens = input.split(" ");

        if (tokens.length != MOVE_COMMAND_TOKEN_SIZE) {
            throw new IllegalArgumentException("[ERROR] 명령은 pass 또는 x y x y 형식이어야 합니다.");
        }

        return new MoveCommand(
                parsePosition(tokens[0], tokens[1]),
                parsePosition(tokens[2], tokens[3])
        );
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
            throw new IllegalArgumentException("[ERROR] 명령은 pass 또는 x y x y 형식이어야 합니다.");
        }
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("[ERROR] 입력이 종료되었습니다.");
        }

        return scanner.nextLine().trim();
    }

    public boolean readContinueAnswer() {
        String input = readLine();

        if (YES.equalsIgnoreCase(input)) {
            return true;
        }

        if (NO.equalsIgnoreCase(input)) {
            return false;
        }

        throw new IllegalArgumentException("[ERROR] y 또는 n 중 하나를 입력해야 합니다.");
    }
}
