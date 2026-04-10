package view;

import controller.command.MoveCommand;
import controller.command.PassCommand;
import controller.command.TurnCommand;
import domain.game.Team;
import domain.position.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final int MOVE_INPUT_COUNT = 4;
    private static final String PASS_INPUT = "pass";
    private final Scanner scanner = new Scanner(System.in);

    public MainMenu askMainMenu() {
        while (true) {
            System.out.println("=== 장기 게임 ===\n1. 새 게임 만들기\n2. 게임 목록에서 이어하기\n3. 종료");
            try {
                return MainMenu.from(Integer.parseInt(scanner.nextLine().trim()));
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String askRoomName() {
        System.out.println("게임방 이름을 입력하세요.");
        return scanner.nextLine().trim();
    }

    public long askRoomId() {
        while (true) {
            System.out.println("이어할 게임방의 ID를 입력하세요.");
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    public int initialFormation(Team team) {
        while (true) {
            System.out.println(team + " 진영 배치 전략을 입력 하세요.\n1. 왼상\n2. 오른상\n3. 원앙마\n4. 양귀마 ");
            try {
                int parseNumber = Integer.parseInt(scanner.nextLine());
                return validRange(parseNumber);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int validRange(int number) {
        if (number < 1 || number > 4) {
            throw new IllegalArgumentException("1 ~ 4 사이의 숫자로 입력해주세요.");
        }
        return number;
    }

    public TurnCommand askTurnCommand(Team team) {
        System.out.println(team + "의 차례입니다. 움직일 기물의 위치와 이동할 위치를 입력하세요. ( 예: 2,5,4,3 / 한 수 쉬기: pass )");
        String input = scanner.nextLine().trim();
        if (PASS_INPUT.equalsIgnoreCase(input)) {
            return new PassCommand();
        }
        return parseMoveCommand(input);
    }

    private MoveCommand parseMoveCommand(String input) {
        List<String> inputs = Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        validatePositionFormat(inputs);
        Position source = Position.from(inputs.get(0), inputs.get(1));
        Position destination = Position.from(inputs.get(2), inputs.get(3));
        return new MoveCommand(source, destination);
    }

    private void validatePositionFormat(List<String> inputs) {
        if (inputs.size() != MOVE_INPUT_COUNT) {
            throw new IllegalArgumentException("예시와 똑같은 형식으로 입력해주세요. (예: 2,5,4,3)");
        }
    }
}
