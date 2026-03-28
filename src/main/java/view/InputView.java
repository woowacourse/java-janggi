package view;

import domain.board.InitializeSetting;
import domain.board.Position;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public InitializeSetting readInitialSetting(String teamName) {
        System.out.println("===" + teamName + " 진영 상차림을 선택하세요 ===");
        System.out.println("1. 왼상차림 (상마상마)");
        System.out.println("2. 오른상차림 (마상마상)");
        System.out.println("3. 안상차림 (마상상마)");
        System.out.println("4. 바깥상차림 (상마마상)");
        System.out.print("선택: ");

        int choice = Integer.parseInt(scanner.nextLine().trim());
        return switch (choice) {
            case 1 -> InitializeSetting.LEFT_ELEPHANT_SETTING;
            case 2 -> InitializeSetting.RIGHT_ELEPHANT_SETTING;
            case 3 -> InitializeSetting.INNER_ELEPHANT_SETTING;
            case 4 -> InitializeSetting.OUTER_ELEPHANT_SETTING;
            default -> throw new IllegalArgumentException("잘못된 선택입니다.");
        };
    }

    public Position[] readMoveCommand() {
        System.out.println("\n이동할 기물의 시작 좌표와 도착 좌표를 입력하세요. (예: 0,0 1,0)");
        System.out.print("입력: ");
        String input = scanner.nextLine().trim();
        String[] parts = input.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("올바른 형식이 아닙니다. (예: 0,0 1,0)");
        }

        return new Position[]{parsePosition(parts[0]), parsePosition(parts[1])};
    }

    private Position parsePosition(String positionStr) {
        String[] coords = positionStr.split(",");
        int x = Integer.parseInt(coords[0].trim());
        int y = Integer.parseInt(coords[1].trim());
        return new Position(x, y);
    }
}
