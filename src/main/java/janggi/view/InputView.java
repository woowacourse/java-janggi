package janggi.view;

import java.util.Scanner;

public class InputView {
    Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getBlueHorsePosition() {
        System.out.println("청팀의 상, 마 순서를 입력해주세요(ex: 상마상마)");
        return scanner.nextLine();
    }

    public String getRedHorsePosition() {
        System.out.println("홍팀의 상, 마 순서를 입력해주세요(ex: 상마상마)");
        return scanner.nextLine();
    }

    public String getPieceMovement() {
        System.out.println("이동 할 기물의 위치와 이동 시킬 위치를 입력해주세요(기물좌표 이동할좌표 ex: 108 88");
        return scanner.nextLine();
    }
}
