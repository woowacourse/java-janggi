package janggi.view;

import janggi.domain.common.Position;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Position readPosition() {
        System.out.println("예시: 1 7 ");
        String input = scanner.nextLine();

        String[] position = input.split(" ");
        return new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    public int readBoardFormationChoice() {
        System.out.println("상차림 번호를 입력하세요");
        return Integer.parseInt(scanner.nextLine());
    }

    public int readOption() {
        return Integer.parseInt(scanner.nextLine());
    }

    public Long readSelectGameId() {
        System.out.println("플레이를 원하는 게임 ID를 입력하세요.");
        return Long.parseLong(scanner.nextLine());
    }
}
