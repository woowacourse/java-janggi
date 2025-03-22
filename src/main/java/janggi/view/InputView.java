package janggi.view;

import janggi.dto.MoveCommandDto;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public MoveCommandDto readMoveCommand() {
        System.out.println("시작위치, 움직일기물, 목적위치를 입력해주세요(예시: 12 마 33)");

        String input = scanner.nextLine();
        validateFormat(input);

        List<String> commands = Arrays.stream(input.split(" "))
                .map(String::trim)
                .toList();

        return MoveCommandDto.from(commands.get(0), commands.get(1), commands.get(2));
    }

    private void validateFormat(String input) {
        if (!input.matches("\\d{2} \\S \\d{2}")) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다.");
        }
    }

    public int readRedSetup() {
        StringBuilder sb = new StringBuilder();
        sb.append("한나라 - 상차림 종류 번호를 입력해주세요. ex) 1\n");
        sb.append(setupTypeNotice());
        System.out.println(sb);

        String input = scanner.nextLine();
        validateNumeric(input);

        return Integer.parseInt(input);
    }

    public int readBlueSetup() {
        StringBuilder sb = new StringBuilder();
        sb.append("초나라 - 상차림 종류 번호를 입력해주세요. ex) 1\n");
        sb.append(setupTypeNotice());
        System.out.println(sb);

        String input = scanner.nextLine();
        validateNumeric(input);

        return Integer.parseInt(input);
    }

    private String setupTypeNotice() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. 마상상마\n")
                .append("2. 상마마상\n")
                .append("3. 마상마상\n")
                .append("4. 상마상마");
        return sb.toString();
    }

    private void validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효한 숫자 입력이 아닙니다.");
        }
    }
}
