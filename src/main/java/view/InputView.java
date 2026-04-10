package view;

import domain.board.InitializeSetting;
import domain.board.Position;

import java.util.Optional;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readMainMenu() {
        System.out.println("장기 게임을 시작합니다.");
        System.out.println("1. 새로하기");
        System.out.println("2. 이어하기");
        System.out.print("선택: ");
        String choice = scanner.nextLine().trim();
        if (!choice.equals("1") && !choice.equals("2")) {
            throw new IllegalArgumentException("1 또는 2를 입력해주세요.");
        }
        return choice;
    }

    public long readGameId() {
        System.out.print("불러올 게임 방 번호(ID)를 입력하세요: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    public InitializeSetting readInitialSetting(String teamName) {
        System.out.println("===" + teamName + " 진영 상차림에 대해 선택하세요(숫자로 입력해주세요.) ===");
        System.out.println("1. 왼상차림 (상마상마)");
        System.out.println("2. 오른상차림 (마상마상)");
        System.out.println("3. 안상차림 (마상상마)");
        System.out.println("4. 바깥상차림 (상마마상)");
        System.out.print("선택: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            return switch (choice) {
                case 1 -> InitializeSetting.LEFT_ELEPHANT_SETTING;
                case 2 -> InitializeSetting.RIGHT_ELEPHANT_SETTING;
                case 3 -> InitializeSetting.INNER_ELEPHANT_SETTING;
                case 4 -> InitializeSetting.OUTER_ELEPHANT_SETTING;
                default -> throw new IllegalArgumentException("1에서 4 사이의 숫자를 입력해주세요.");
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("문자가 아닌 숫자를 입력해야 합니다.");
        }
    }

    public Optional<Position> readSourcePosition() {
        System.out.println("\n움직일 기물의 좌표를 입력하세요. (예: A0)");
        System.out.println("게임을 중단하고 저장하려면 'Q'를 입력하세요.");
        System.out.print("출발지: ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("Q")) {
            return Optional.empty();
        }

        return Optional.of(parsePosition(input));
    }

    public Position readTargetPosition() {
        System.out.print("도착지: ");
        return parsePosition(scanner.nextLine());
    }

    private Position parsePosition(String input) {
        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        if (cleaned.length() != 2) {
            throw new IllegalArgumentException("올바른 형식이 아닙니다. (예: A0)");
        }

        char xChar = cleaned.charAt(0);
        char yChar = cleaned.charAt(1);

        if (xChar < 'A' || xChar > 'I') {
            throw new IllegalArgumentException("X 좌표는 A부터 I 사이의 알파벳이어야 합니다.");
        }
        if (yChar < '0' || yChar > '9') {
            throw new IllegalArgumentException("Y 좌표는 0부터 9 사이의 숫자이어야 합니다.");
        }

        int x = xChar - 'A';
        int y = yChar - '0';

        return new Position(x, y);
    }
}
