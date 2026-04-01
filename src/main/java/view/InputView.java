package view;

import domain.Team;
import domain.vo.Position;

import java.util.Scanner;

public class InputView {

    private static final String POSITION_PATTERN = "^\\d+\\s+\\d+$";
    final Scanner scanner = new Scanner(System.in);

    public Position readPosition() {
        System.out.println("움직일 기물의 위치를 입력해주세요. (예: 0 0)");

        String input = scanner.nextLine();
        try {
            validatePositionFormat(input);

            String[] tokens = input.split(" ");
            return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return readPosition();
        }
    }

    public Position readTargetPosition() {
        System.out.println("기물을 움직일 위치를 입력해주세요. (예: 0 0)");

        String input = scanner.nextLine();
        try {
            validatePositionFormat(input);

            String[] tokens = input.split(" ");
            return Position.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return readTargetPosition();
        }
    }

    public Boolean readRetryCommand() {
        System.out.println("계속 하시겠습니까?(y/n)");

        String input = scanner.nextLine();
        try {
            validateRetryCommand(input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return readRetryCommand();
        }

        return input.equals("y");
    }

    public String readArrangement(Team team) {
        System.out.println(team.getName() + "나라의 판차림 방식을 선택해주세요.(1: 상마상마, 2: 마상마상, 3: 마상상마, 4: 상마마상");

        return scanner.nextLine();
    }

    private void validateRetryCommand(final String input) {
        if (!input.equals("y") && !input.equals("n")) {
            throw new IllegalArgumentException("[ERROR] y 또는 n만 입력 가능합니다.");
        }
    }

    private void validatePositionFormat(final String input) {
        if (!input.matches(POSITION_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. '숫자 공백 숫자' 형식이어야 합니다.");
        }
    }
}
