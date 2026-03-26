package janggi.view;

import java.util.List;
import java.util.Scanner;

public class InputView {
    Scanner scanner = new Scanner(System.in);

    public int readBoardInitializeType() {
        System.out.println();

        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }

    public List<Integer> readPositions() {
        String input = scanner.nextLine();
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("위치는 '행,열' 형식으로 입력하세요. (예: 3,5)");
        }
        return List.of(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

}
