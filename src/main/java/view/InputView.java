package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String getFirstPlayerName() {
        System.out.println("첫번째 플레이어의 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public String getSecondPlayerName() {
        System.out.println("두번째 플레이어의 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public String getStartPlayerName() {
        System.out.println("먼저 시작할 플레이어의 이름을 입력해주세요.");
        return scanner.nextLine();
    }

    public String getSetupNumber(String playerName) {
        System.out.printf("%s의 배치를 선택해주세요\n", playerName);
        System.out.println("1. Inner Elephant Setup");
        System.out.println("2. Outer Elephant Setup");
        System.out.println("3. Right Elephant Setup");
        System.out.println("4. Left Elephant Setup");

        return scanner.nextLine().trim();
    }


}
