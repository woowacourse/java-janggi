package view;

import domain.piece.character.Team;
import domain.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import util.ErrorHandler;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String inputGameRoomName() {
        System.out.printf("%n입장할 게임방 이름을 입력해주세요.%n> ");
        return scanner.nextLine();
    }

    public static SangMaOrderCommand inputSangMaOrder(Team team) {
        return ErrorHandler.retryUntilSuccessWithReturn(() -> {
            System.out.printf("""
                    %n%s나라 상마 순서를 입력해주세요. (예: 3)
                    1. 상마상마
                    2. 상마마상
                    3. 마상상마
                    4. 마상마상
                    >\s""", teamToString(team));
            String input = scanner.nextLine();
            return SangMaOrderCommand.from(input);
        });
    }

    public static Command inputCommand() {
        return ErrorHandler.retryUntilSuccessWithReturn(() -> {
            System.out.printf("""
                    %n원하는 기능을 선택해주세요. (예: move)
                    > move: 기물 이동
                    > status: 현재 점수 출력
                    > end: 게임 종료
                    >\s""");
            String input = scanner.nextLine();
            return Command.from(input.strip());
        });
    }

    public static MoveCommand inputMoveCommand(Team team) {
        return ErrorHandler.retryUntilSuccessWithReturn(() -> {
            System.out.printf("""
                    %n현재 턴: %s나라
                    이동할 기물의 위치와 이동할 위치를 입력해주세요. (예: 7,1 7,2)
                    >\s""", teamToString(team));
            String input = scanner.nextLine();

            List<String> parsed = Arrays.stream(input.split(" ", -1)).toList();
            validateMoveCommandSize(parsed);

            List<String> source = Arrays.stream(parsed.get(0).split(",", -1)).toList();
            List<String> destination = Arrays.stream(parsed.get(1).split(",", -1)).toList();
            validatePoint(source);
            validatePoint(destination);

            Point sourcePoint = Point.of(stringToInteger(source.get(0)), stringToInteger(source.get(1)));
            Point destinationPoint = Point.of(stringToInteger(destination.get(0)), stringToInteger(destination.get(1)));

            return new MoveCommand(sourcePoint, destinationPoint);
        });
    }

    private static String teamToString(Team team) {
        return switch (team) {
            case CHO -> Painter.paintGreen("초");
            case HAN -> Painter.paintRed("한");
        };
    }

    private static int stringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수를 입력해주세요.");
        }
    }

    private static void validateMoveCommandSize(List<String> parsed) {
        if (parsed.size() != 2) {
            throw new IllegalArgumentException(parsed + ": [ERROR] 위치를 2개 입력해주세요. (예: 1,2)");
        }
    }

    private static void validatePoint(List<String> point) {
        if (point.size() != 2) {
            throw new IllegalArgumentException(point + ": [ERROR] 위치 정보를 올바르게 입력해주세요. (예: 1,1)");
        }
    }
}
