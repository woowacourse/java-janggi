package view;

import domain.Camp;
import domain.ElephantFormation;
import domain.Position;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final Map<Camp, String> CAMP_NAMES = new EnumMap<>(
            Map.of(Camp.CHO, "초나라", Camp.HAN, "한나라"));
    private static final Map<ElephantFormation, String> FORMATION_NAMES = new EnumMap<>(
            ElephantFormation.class);

    static {
        FORMATION_NAMES.put(ElephantFormation.RIGHT, "1. [마 상 마 상]");
        FORMATION_NAMES.put(ElephantFormation.INNER, "2. [마 상 상 마]");
        FORMATION_NAMES.put(ElephantFormation.LEFT, "3. [상 마 상 마]");
        FORMATION_NAMES.put(ElephantFormation.OUTER, "4. [상 마 마 상]");
    }

    private final Scanner sc = new Scanner(System.in);

    public int askElephantFormation(Camp camp) {
        return readElephantFormation(camp);
    }

    public Position readFromPosition(Camp camp) {
        while (true) {
            try {
                System.out.println(CAMP_NAMES.get(camp)
                        + " 플레이어는 움직일 말의 위치를 column, row 형식으로 입력해 주세요. (입력좌표 예시 column, row: 0, 9)");
                return readPosition();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Position readToPosition(Camp camp) {
        while (true) {
            try {
                System.out.println(CAMP_NAMES.get(camp)
                        + " 플레이어는 선택한 말을 움직일 위치를 column, row 형식으로 입력해 주세요.(입력좌표 예시 column, row: 0, 7)");
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
                for (String name : FORMATION_NAMES.values()) {
                    System.out.println(name);
                }
                return validateFormationNum(parseInt(sc.nextLine()));
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

    private int validateFormationNum(int formationNum) {
        if (formationNum < 0 || formationNum > FORMATION_NAMES.size()) {
            throw new IllegalArgumentException("[ERROR] 상차림 정보는 1 ~ 4 사이어야 합니다.");
        }
        return formationNum;
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
