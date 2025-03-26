package view;

import domain.BoardPosition;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String POSITION_DELIMITER = ",";

    private static final Scanner scanner = new Scanner(System.in);

    public List<Integer> inputSelectPosition() {
        System.out.println("이동할 기물의 위치를 입력하세요. (예시: 1,1)");
        final String input = readInput();

        return validateAndParse(validateAndSplit(input));
    }

    public List<Integer> inputDestinationPosition() {
        System.out.println("이동할 위치를 입력하세요. (예시: 1,1)");
        final String input = readInput();
        
        return validateAndParse(validateAndSplit(input));
    }

    private String[] validateAndSplit(final String input) {
        final String[] coordinates;
        try {
            coordinates = input.split(POSITION_DELIMITER, -1);
        } catch (Exception e) {
            throw new IllegalArgumentException("좌표는 'x,y' 형식으로 입력해야 합니다.");
        }

        if (coordinates.length != BoardPosition.COORDINATE_PARTS_COUNT) {
            throw new IllegalArgumentException("좌표는 'x,y' 형식으로 입력해야 합니다.");
        }

        return coordinates;
    }

    private List<Integer> validateAndParse(final String[] parts) {
        try {
            final int x = Integer.parseInt(parts[0].trim());
            final int y = Integer.parseInt(parts[1].trim());

            return List.of(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자로 입력해야 합니다.");
        }
    }

    private String readInput() {
        return scanner.nextLine();
    }
}
