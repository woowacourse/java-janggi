package view;

import domain.JanggiPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final int BOARD_HEIGHT = 10;
    private static final int BOARD_WIDTH = 10;

    public static Long inputGameId() {
        System.out.println("이어하고 싶은 게임 ID를 입력해주세요. 새롭게 게임을 시작하려면 -1이라고 입력해주세요.");
        String id = scanner.nextLine();
        return Long.parseLong(id);
    }

    public static Long inputNewGameId() {
        System.out.println("새롭게 만들 게임 ID를 입력해주세요.");
        String id = scanner.nextLine();
        return Long.parseLong(id);
    }

    public static String input() {
        System.out.println("이동할 기물의 위치와 도착 위치를 입력하세요 (예: 10 12, 종료: q):");
        return scanner.nextLine();
    }

    public static List<JanggiPosition> parsePositions(String input) {
        validateWithBlank(input);
        String[] positions = input.split(" ");
        validatePositionsRange(positions);

        JanggiPosition beforePosition = new JanggiPosition(Character.getNumericValue(positions[0].charAt(0)),
                Character.getNumericValue(positions[0].charAt(1)));
        JanggiPosition afterPosition = new JanggiPosition(Character.getNumericValue(positions[1].charAt(0)),
                Character.getNumericValue(positions[1].charAt(1)));

        List<JanggiPosition> janggiPositions = new ArrayList<>();
        janggiPositions.add(beforePosition);
        janggiPositions.add(afterPosition);

        return janggiPositions;
    }

    private static void validateWithBlank(String string) {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("입력값이 없습니다. 올바른 좌표를 입력해주세요.");
        }
        if (string.split(" ").length != 2) {
            throw new IllegalArgumentException("올바른 좌표를 입력해주세요.");
        }
    }

    private static void validatePositionsRange(String[] positions) {
        for (String positionStr : positions) {
            if (positionStr.length() != 2 || !Character.isDigit(positionStr.charAt(0)) || !Character.isDigit(
                    positionStr.charAt(1))) {
                throw new IllegalArgumentException("좌표는 두 자리 숫자로 입력해야 합니다.");
            }
            int file = Character.getNumericValue(positionStr.charAt(0));
            int rank = Character.getNumericValue(positionStr.charAt(1));

            if (rank < 0 || rank >= BOARD_WIDTH || file < 1 || file >= BOARD_HEIGHT) {
                throw new IllegalArgumentException(
                        String.format("좌표가 장기판 범위를 벗어났습니다. 세로는 0-9, 가로는 1-9여야 합니다. 입력된 좌표 : %d%d", file, rank));
            }
        }
    }
}
