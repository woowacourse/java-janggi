package view;

import domain.board.Point;
import domain.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static String MOVE_COMMAND_INPUT = "move";

    public static SangMaOrderCommand inputSangMaOrder(Team team) {
        System.out.printf("""
                %n%s나라 상마 순서를 입력해주세요.
                1. 상마상마
                2. 상마마상
                3. 마상상마
                4. 마상마상
                """, team.title());
        String input = scanner.nextLine();
        return SangMaOrderCommand.from(input);
    }

    public static MoveCommand inputMoveCommand(Team team) {
        System.out.printf("""
                %n현재 턴 : %s나라
                이동할 기물의 현재 위치와 이동할 위치를 입력해주세요. (예: move 1,1 2,1)
                """, team.title());
        String input = scanner.nextLine();

        List<String> parsed = Arrays.stream(input.split(" ", -1)).toList();
        String command = parsed.get(0);
        validateCommand(command);

        List<String> source = Arrays.stream(parsed.get(1).split(",", -1)).toList();
        List<String> destination = Arrays.stream(parsed.get(2).split(",", -1)).toList();
        validateSize(source);
        validateSize(destination);

        Point sourcePoint = Point.of(convertToInteger(source.get(0)), convertToInteger(source.get(1)));
        Point destinationPoint = Point.of(convertToInteger(destination.get(0)), convertToInteger(destination.get(1)));

        return new MoveCommand(sourcePoint, destinationPoint);
    }

    private static void validateCommand(String command) {
        if (!MOVE_COMMAND_INPUT.equals(command)) {
            throw new IllegalArgumentException("[ERROR] 올바른 커맨드를 입력해주세요.");
        }
    }

    private static void validateSize(List<String> point) {
        if (point.size() != 2) {
            throw new IllegalArgumentException(point + ": [ERROR] 위치 정보를 올바르게 입력해주세요. (예: 1,1)");
        }
    }

    private static int convertToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수를 입력해주세요.");
        }
    }
}
