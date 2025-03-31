package view;

import java.util.Scanner;

import board.Position;
import game.Turn;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public Position readStartPosition(final Turn turn) {
        System.out.printf("이동하고자 하는 기물의 위치를 입력해주세요. 현재 턴: %s%n", turn.getCurrentTurnTeam().name());
        String[] tokens = scanner.nextLine()
                .split(",");
        return new Position(parse(tokens[0]), parse(tokens[1]));
    }

    public Position readDestinationPosition() {
        System.out.println("기물이 이동하고자 하는 위치를 입력해주세요.");
        String[] tokens = scanner.nextLine()
                .split(",");
        return new Position(parse(tokens[0]), parse(tokens[1]));
    }

    private int parse(final String number) {
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
