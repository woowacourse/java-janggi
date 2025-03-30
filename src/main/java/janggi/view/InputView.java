package janggi.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMoveStartPosition() {
        System.out.println("이동할 말의 위치를 입력하세요. (yx축 순서 예: 01)");
        return scanner.nextLine();
    }

    public String inputMoveEndPosition() {
        System.out.println("목적지 위치를 입력하세요. (yx축 순서 예: 01)");
        return scanner.nextLine();
    }

    public String inputIsNewGame() {
        System.out.println("새로운 게임을 시작하시겠습니까? (new/continue)");
        return scanner.nextLine();
    }
}
