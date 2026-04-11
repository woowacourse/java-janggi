package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readPlayerName(String teamName) {
        System.out.print(teamName + " 플레이어 이름을 입력하세요: ");
        return scanner.nextLine();
    }

    public static String readFormation(String teamName) {
        System.out.println(teamName + "의 차림을 입력해주세요: ");
        return scanner.nextLine();
    }

    public static String readMoveCommand(String teamName) {
        System.out.println(teamName + " 이동 입력 (예: 졸 7,1 -> 6,1) : ");
        return scanner.nextLine();
    }

    public static int choiceGame() {
        System.out.println("1. 새 게임 시작");
        System.out.println("2. 이어하기");
        System.out.println("선택하세요: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }
}