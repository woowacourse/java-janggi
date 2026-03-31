package view;

import domain.vo.Position;

import java.util.Scanner;

public class InputView {

    private static final String POSITION_PATTERN = "^\\d+\\s+\\d+$";
    private final Scanner scanner = new Scanner(System.in);

    public int readHorseElephantFormation(String team) {
        System.out.println(team + "의 초기 진형을 선택하세요. (숫자만 입력)");
        System.out.println("1. 마상마상");
        System.out.println("2. 상마상마");

        String input = scanner.nextLine().trim();
        try {
            int parsedInput = Integer.parseInt(input);
            if (parsedInput != 1 && parsedInput != 2)
                throw new IllegalArgumentException();
            System.out.println();
            return parsedInput;
        }
        catch (Exception e) {
            System.out.println("[ERROR] 잘못된 입력입니다.");
            System.out.println();
            return readHorseElephantFormation(team);
        }
    }
    
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
