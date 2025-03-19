package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public List<String> readMoveCommand() {
        System.out.println("시작위치, 움직일기물, 목적위치를 입력해주세요(예시: 12 마 33)");
        String input = scanner.nextLine();
        return Arrays.stream(input.split(" "))
                .map(String::trim)
                .toList();
    }
}
