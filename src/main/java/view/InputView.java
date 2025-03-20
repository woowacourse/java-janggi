package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String inputMovePiecePosition() {
        System.out.println("이동하고자 하는 기물의 위치를 입력해주세요.");
        return scanner.nextLine();
    }

    public String inputMoveTargetPosition() {
        System.out.println("기물이 이동하고자 하는 위치를 입력해주세요.");
        return scanner.nextLine();
    }
}
