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

    public String inputSelectJanggi() {
        System.out.println("진행할 게임 번호를 입력하시거나, new를 입력하여 새로운 게임을 만들어주세요.");
        return readInput();
    }

    public String inputJanggiTitle() {
        System.out.println("생성할 장기 게임 제목을 입력해주세요.");
        return readInput();
    }

    private String readInput() {
        return scanner.nextLine();
    }
}
