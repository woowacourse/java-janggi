package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readChoPlayerName() {
        System.out.println("초나라 플레이어 이름 입력: ");
        return readLine();
    }

    public String readHanPlayerName() {
        System.out.println("한나라 플레이어 이름 입력: ");
        return readLine();
    }

    public String readChoFormation() {
        System.out.println("초나라 플레이어 포지션 입력: ");
        return readLine();
    }

    public String readHanFormation() {
        System.out.println("한나라 플레이어 포지션 입력: ");
        return readLine();
    }

    private String readLine() {
        return scanner.nextLine();
    }
}
