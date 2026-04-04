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

    public String readArrangement(final Team team) {
        System.out.println(team.getName() + "나라의 판차림 방식을 선택해주세요.(상마상마, 마상마상, 마상상마, 상마마상)");

        return scanner.nextLine();
    }

    private void validatePositionFormat(final String input) {
        if (input.equals("항복")) {
            System.out.println("플레이어의 항복으로 게임을 종료합니다.");
            System.exit(0);
        }

        if (!input.matches(POSITION_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. '숫자 공백 숫자' 형식이어야 합니다.");
        }
    }
}
