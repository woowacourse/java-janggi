package view;

import domain.board.Point;
import domain.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static ProgressCommand inputProgress() {
        System.out.printf("""
                %n> 원하는 진행 커맨드를 입력해주세요. (예: start)
                - 게임 시작: start
                - 기물 이동: move
                - 점수 확인: status
                - 게임 종료: exit
                """);
        String input = scanner.nextLine();
        return ProgressCommand.from(input);
    }

    public static SangMaOrderCommand inputSangMaOrder(final Team team) {
        System.out.printf("""
                %n> %s나라 상마 순서 번호를 입력해주세요. (예: 3)
                1. 상마상마
                2. 상마마상
                3. 마상상마
                4. 마상마상
                """, TeamView.title(team));
        String input = scanner.nextLine();
        return SangMaOrderCommand.from(input);
    }

    public static MoveCommand inputMoveCommand() {
        System.out.println("> 이동할 기물의 현재 위치와 이동할 위치를 입력해주세요. (예: 1,1 2,1)");
        String input = scanner.nextLine();

        List<String> parsed = Arrays.stream(input.split(" ", -1)).toList();
        List<String> source = Arrays.stream(parsed.get(0).split(",", -1)).toList();
        List<String> destination = Arrays.stream(parsed.get(1).split(",", -1)).toList();

        validateSize(source);
        validateSize(destination);

        Point sourcePoint = Point.of(convertToInteger(source.get(0)), convertToInteger(source.get(1)));
        Point destinationPoint = Point.of(convertToInteger(destination.get(0)), convertToInteger(destination.get(1)));

        return new MoveCommand(sourcePoint, destinationPoint);
    }

    private static void validateSize(final List<String> point) {
        if (point.size() != 2) {
            throw new IllegalArgumentException(point + ": [ERROR] 위치 정보를 올바르게 입력해주세요. (예: 1,1 2,1)");
        }
    }

    private static int convertToInteger(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(input + ": [ERROR] 위치 정보는 정수로 입력해주세요.");
        }
    }
}
