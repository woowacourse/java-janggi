package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readMovePiecePosition(String teamName) {
        System.out.print(teamName + "나라의 턴입니다. 이동할 기물의 좌표를 입력해주세요.(e.g. 0,0)\n");
        return scanner.nextLine();
    }

    public static String readTargetPiecePosition() {
        System.out.print("어떤 좌표로 이동하시겠습니까?\n");
        return scanner.nextLine();
    }

    public static String askResumeGame() {
        System.out.println("중단된 게임이 있습니다. 이어서 진행하시겠습니까?");
        return scanner.nextLine();
    }
}
