package view;

import java.util.Scanner;

import domain.Position;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public Position inputMovePiecePosition() {
        System.out.println("이동하고자 하는 기물의 위치를 입력해주세요.");
        String[] tokens = scanner.nextLine()
                .split(",");
        return new Position(parse(tokens[0]), parse(tokens[1]));
    }

    public Position inputMoveTargetPosition() {
        System.out.println("기물이 이동하고자 하는 위치를 입력해주세요.");
        String[] tokens = scanner.nextLine()
                .split(",");
        return new Position(parse(tokens[0]), parse(tokens[1]));
    }

    public boolean inputExitGame() {
        System.out.println("게임을 종료하시겠습니까?(y/n)");
        return scanner.nextLine().equals("y");
    }

    private int parse(String number) {
        try {
            if (number.equals("0")) {
                return 10;
            }
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해 주세요.");
        }
    }

}
