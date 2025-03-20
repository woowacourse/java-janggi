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
}
