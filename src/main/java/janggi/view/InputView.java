package janggi.view;

import janggi.domain.Position;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readString() {
        return scanner.nextLine().trim();
    }

    public Position parsePosition(String input) {
        String[] splitInput = input.split(" ");
        try {
            return new Position(Integer.parseInt(splitInput[0]), Integer.parseInt(splitInput[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 좌표는 숫자 조합이어야 합니다. (예: 1 7)");
        }
    }
}
