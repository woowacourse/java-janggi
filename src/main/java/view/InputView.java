package view;

import domain.common.Side;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readPlayerName(Side side) {
        System.out.printf("%s나라 플레이어 이름 입력: ", side.getName());
        return scanner.nextLine();
    }

    public String readGameStartCommand() {
        System.out.println("""
                게임 시작 메뉴를 선택하세요.
                1. 새로 시작
                2. 게임 불러오기""");
        return scanner.nextLine();
    }

    public String readSavedGameNumber() {
        System.out.println("불러올 게임 번호를 입력하세요.");
        return scanner.nextLine();
    }

    public String readFormation(Side side) {
        String message = String.format("""
            %s나라 플레이어 포메이션 입력
            1. 상마상마
            2. 마상마상
            3. 상마마상
            4. 마상상마""", side.getName());

        System.out.println(message);
        return scanner.nextLine();
    }

    public String readSourcePosition(Side side) {
        System.out.println(side.getName() + "나라 플레이어 차례입니다. 이동 시킬 기물의 위치를 입력하세요. 예) 1,3");
        return scanner.nextLine();
    }

    public String readTargetPosition() {
        System.out.println("선택한 기물이 이동할 수 있는 위치입니다. 이동할 위치를 입력하세요. 예) 1,3");
        return scanner.nextLine();
    }
}
