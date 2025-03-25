package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    private InputView() {
    }

    public static List<String> readToPoint() {
        final String input = prompt("이동할 곳의 좌표를 입력해주세요.(예: 1,1)");
        final List<String> numbers = Arrays.asList(input.strip().split(","));
        validateSize(numbers);
        return numbers;
    }

    public static List<String> readFromPoint() {
        final String input = prompt("움직일 말의 좌표를 입력해주세요.(예: 1,1)");
        final List<String> numbers = Arrays.asList(input.strip().split(","));
        validateSize(numbers);
        return numbers;
    }

    private static String prompt(final String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private static void validateSize(final List<String> numbers) {
        if (numbers.size() != 2) {
            throw new IllegalArgumentException("좌표는 (숫자,숫자) 형식으로 입력해주세요.");
        }
    }
}
