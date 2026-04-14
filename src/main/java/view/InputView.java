package view;

import static view.Formatter.CAMP_NAMES;
import static view.Formatter.FORMATION_NAMES;

import domain.Camp;
import domain.Position;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

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
