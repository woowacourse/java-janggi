package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputSelectPosition() {
        System.out.println("이동할 기물의 위치를 입력하세요. (예시: 1,1)");
        return readInput();
    }

    public String inputDestinationPosition() {
        System.out.println("이동할 위치를 입력하세요. (예시: 1,1)");
        return readInput();
    }

    private String readInput() {
        return scanner.nextLine();
    }
}
