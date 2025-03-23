package janggi.view;

import janggi.point.Point;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private static final String ERROR_SUFFIX = " 다시 입력해주세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public boolean readGameStart() {
        try {
            System.out.println("게임을 진행하시겠습니까? (y/n)");
            return YorN.fromText(scanner.nextLine()).toBoolean();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readGameStart();
        }
    }

    public Point readStartPoint() {
        try {
            System.out.println("이동하고 싶은 기물의 좌표를 입력하세요. (row,column)");
            return parseStringToPoint(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readStartPoint();
        }
    }

    public Point readTargetPoint() {
        try {
            System.out.println("어디로 이동하시겠습니까? (row,column)");
            return parseStringToPoint(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + ERROR_SUFFIX);
            return readTargetPoint();
        }
    }

    private Point parseStringToPoint(String input) {
        String[] rawPoint = input.split(",", -1);
        validatePointSize(rawPoint);
        return new Point(parseStringToInteger(rawPoint[0]), parseStringToInteger(rawPoint[1]));
    }

    private void validatePointSize(String[] rawPoint) {
        if (rawPoint.length != 2) {
            throw new IllegalArgumentException("2차원 좌표만 입력 가능합니다.");
        }
    }

    private int parseStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자만 입력 가능합니다.");
        }
    }

    enum YorN {
        YES("y", true),
        NO("n", false);

        private final String text;
        private final boolean isYes;

        YorN(String text, boolean isYes) {
            this.text = text;
            this.isYes = isYes;
        }

        private static YorN fromText(String input) {
            return Arrays.stream(values())
                .filter(value -> value.text.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력은 y/n만 가능합니다."));
        }

        private boolean toBoolean() {
            return isYes;
        }
    }
}
