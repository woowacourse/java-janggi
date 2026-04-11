package view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputSideChoice() {
        System.out.println();
        System.out.println("연장자나 상급자가 양손에 색깔 하나씩을 숨겼습니다.");
        System.out.println("하급자는 왼속, 오른손 중에서 하나 선택해주세요.");
        System.out.println("1. 왼손");
        System.out.println("2. 오른손");

        return scanner.nextLine();
    }

    public static String inputHanPlacementCode() {
        System.out.println("한 진영은 배치를 선택 해주세요");
        printPlacementChoice();

        return scanner.nextLine();
    }

    public static String inputChoPlacementCode() {
        System.out.println("초 진영은 배치를 선택 해주세요");
        printPlacementChoice();

        return scanner.nextLine();
    }

    public static String inputFromPosition() {
        System.out.println("움직일 기물의 좌표를 입력해주세요. (쉼표로 구분)");
        return scanner.nextLine();
    }

    public static String inputToPosition() {
        System.out.println("기물을 놓을 좌표를 입력해주세요. (쉼표로 구분)");
        return scanner.nextLine();
    }

    private static void printPlacementChoice() {
        System.out.println("1. 내부 코끼리 (마상상마)");
        System.out.println("2. 외부 코끼리 (상마마상)");
        System.out.println("3. 우측 코끼리 (마상마상)");
        System.out.println("4. 좌측 코끼리 (상마상마)");
    }
}
