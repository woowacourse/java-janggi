package view;

import domain.Camp;
import domain.Position;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public int askElephantFormation(Camp camp) {
        return readElephantFormation(camp);
    }

    public Position askFromPosition(Camp camp) {
        while (true) {
            try {
                System.out.println(camp.getCampName() + " 플레이어는 말을 선택해주세요. (입력좌표 예시: 2, 1)");
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
                        camp.getCampName() + " 플레이어는 선택한 말을 움직일 위치를 입력해 주세요.(입력좌표 예시: 2, 3)");
                return readPosition();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readElephantFormation(Camp camp) {
        while (true) {
            try {
                System.out.println(camp.getCampName() + " 상 차림을 결정해주세요.");
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
        System.out.print("좌표: ");
        String[] input = Arrays.stream(sc.nextLine().split(","))
                .map(String::trim)
                .toArray(String[]::new);

        validateInputSize(input);
        int x = parseInt(input[0]);
        int y = parseInt(input[1]);
        System.out.println();
        return new Position(x, y);
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
