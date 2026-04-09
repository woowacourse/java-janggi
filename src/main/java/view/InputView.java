package view;

import domain.piece.Side;
import domain.position.Position;

import java.util.Scanner;

public class InputView {

    private InputView() {}

    private static final Scanner scanner = new Scanner(System.in);

    public static int inputSideChoice() {
        printSideChoiceMessage();
        String inputSideCode = scanner.nextLine();
        if (!inputSideCode.matches("^[1|2]$")) {
            throw new IllegalArgumentException("코드는 1, 2만 입력 가능합니다.");
        }
        return Integer.parseInt(inputSideCode);
    }

    public static int inputPlacementCodeBy(Side side) {
        System.out.println(side.getName() + " 진영은 배치를 선택해주세요.");
        printPlacementChoice();
        String input = scanner.nextLine();
        if (!input.matches("^[1|2|3|4]$")) {
            throw new IllegalArgumentException("코드는 1, 2, 3, 4만 입력 가능합니다.");
        }
        return Integer.parseInt(input);
    }

    public static Position inputStartPosition(String currentTurn) {
        System.out.println(currentTurn + "진영은 시작 위치를 입력해주세요. ex) 1,2");
        return inputAndParseToPosition();
    }

    public static Position inputEndPosition(String currentTurn) {
        System.out.println(currentTurn + "진영은 도착 위치를 입력해주세요. ex) 1,2");
        return inputAndParseToPosition();
    }

    private static void printSideChoiceMessage() {
        System.out.println("연장자나 상급자가 양손에 색깔 하나씩을 숨겼습니다.");
        System.out.println("하급자는 왼속, 오른손 중에서 하나 선택해주세요.");
        System.out.println("1. 왼손");
        System.out.println("2. 오른손");
        System.out.println();
    }

    private static void printPlacementChoice() {
        System.out.println("1. 내부 코끼리 (마상상마)");
        System.out.println("2. 외부 코끼리 (상마마상)");
        System.out.println("3. 우측 코끼리 (마상마상)");
        System.out.println("4. 좌측 코끼리 (상마상마)");
        System.out.println();
    }

    private static Position inputAndParseToPosition() {
        String input = scanner.nextLine();

        if (!input.matches("^(10|[1-9]),[1-9]$")) {
            throw new IllegalArgumentException("좌표를 row는 1-10, column은 1-9 까지만 가능합니다.");
        }
        String[] inputPosition = input.split(",");
        return Position.of(Integer.parseInt(inputPosition[0]), Integer.parseInt(inputPosition[1]));
    }

    public static int inputGameStartChoice() {
        System.out.println("게임 시작 선택지를 선택해주세요.");
        System.out.println("1. 새로운 게임 시작");
        System.out.println("2. 기존 게임 이어서 진행");
        System.out.println("2. 종료");

        String input = scanner.nextLine();
        if (!input.matches("^[1-3]$")) {
            throw new IllegalArgumentException("게임 선택지는 1,2,3만 가능합니다.");
        }
        return Integer.parseInt(input);
    }

    public static Long inputExistingGameId() {
        System.out.println("이어서 진행할 게임 Id를 입력해주세요");
        String input = scanner.nextLine();
        if (!input.matches("^[1-9][0-9]*$")) {
            throw new IllegalArgumentException("1이상의 양수만 가능합니다.");
        }
        return Long.parseLong(input);
    }
}
