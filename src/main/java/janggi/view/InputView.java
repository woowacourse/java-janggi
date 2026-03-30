package janggi.view;

import janggi.domain.Position;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Position readPosition() {
        System.out.println("예시: 1 7 ");
        String input = scanner.nextLine();

        String[] position = input.split(" ");
        return new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }
}
