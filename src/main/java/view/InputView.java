package view;

import domain.Camp;
import domain.Position;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private final Scanner sc = new Scanner(System.in);
    private static final Map<Camp, String> CAMP_NAMES = new EnumMap<>(
            Map.of(Camp.CHO, "초나라", Camp.HAN, "한나라"));

    public int askElephantFormation(Camp camp) {
        return readElephantFormation(camp);
    }

    public Position askFromPosition(Camp camp) {
        while (true) {
            try {
                System.out.println(CAMP_NAMES.get(camp) + " 플레이어는 움직일 말의 위치를 column, row 형식으로 입력해 주세요. (입력좌표 예시 column, row: 0, 9)");
                return readPosition();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Position askToPosition(Camp camp) {
        while (true) {
            try {
                System.out.println(
                        CAMP_NAMES.get(camp) + " 플레이어는 선택한 말을 움직일 위치를 column, row 형식으로 입력해 주세요.(입력좌표 예시 column, row: 0, 7)");
                return readPosition();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readElephantFormation(Camp camp) {
        while (true) {
            try {
                System.out.println(CAMP_NAMES.get(camp) + " 상 차림을 결정해주세요.");
                System.out.println("1. [마 상 마 상]");
                System.out.println("2. [마 상 상 마]");
                System.out.println("3. [상 마 상 마]");
                System.out.println("4. [상 마 마 상]");
                return Integer.parseInt(sc.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Position readPosition() {
        System.out.print("좌표(column, row): ");
        String[] input = Arrays.stream(sc.nextLine().split(",")).map(String::trim)
                .toArray(String[]::new);

        validateInputSize(input);
        int column = parseInt(input[0]);
        int row = parseInt(input[1]);
        System.out.println();
        return new Position(column, row);
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해 주세요");
        }
    }

    private void validateInputSize(String[] input) {
        if (input.length != 2) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자 2개 입니다.");
        }
    }
}
