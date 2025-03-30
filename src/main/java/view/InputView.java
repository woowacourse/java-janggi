package view;

import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static JanggiPosition readStartPosition() {
        while (true) {
            try {
                System.out.println("움직이려는 기물의 행 번호를 입력해주세요.");
                final int row = readNumber();
                System.out.println("움직이려는 기물의 열 번호를 입력해주세요.");
                final int col = readNumber();
                return JanggiPositionFactory.of(row, col);
            } catch (IllegalArgumentException e) {
                System.out.printf("%s 다시 입력해주세요.\n", e.getMessage());
            }
        }
    }

    public static JanggiPosition readDestinationPosition() {
        while (true) {
            try {
                System.out.println("목적지의 행 번호를 입력해주세요.");
                final int row = readNumber();
                System.out.println("목적지의 열 번호를 입력해주세요.");
                final int col = readNumber();
                return JanggiPositionFactory.of(row, col);
            } catch (IllegalArgumentException e) {
                System.out.printf("%s 다시 입력해주세요.\n", e.getMessage());
            }
        }
    }

    private static int readNumber() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자 형식이 올바르지 않습니다. 다시 입력해주세요.");
            }
        }
    }
}
