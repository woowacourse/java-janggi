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
        return Arrays.asList(input.split(","));
    }

    public static List<String> readFromPoint() {
        final String input = prompt("움직일 말의 좌표를 입력해주세요.(예: 1,1)");
        return Arrays.asList(input.split(","));
    }

    private static String prompt(final String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
