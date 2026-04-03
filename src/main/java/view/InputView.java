package view;

import domain.board.SangSetupType;
import java.util.Scanner;
import domain.position.Position;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public SangSetupType readSangSetupType() {
        try {
            int number = Integer.parseInt(scanner.nextLine());
            return SangSetupType.from(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("상차림 번호는 숫자로 입력해야 합니다.");
        }
    }

    public Position readDeparturePosition() {
        return readPosition("출발지 좌표를 입력하세요. (row,column)");
    }

    public Position readDestinationPosition() {
        return readPosition("도착지 좌표를 입력하세요. (row,column)");
    }

    private Position readPosition(String message) {
        System.out.println(message);
        String[] values = splitPositionInput(scanner.nextLine());
        return parsePosition(values);
    }

    private String[] splitPositionInput(String input) {
        String[] values = input.split(",");
        if (values.length != 2) {
            throw new IllegalArgumentException("좌표는 row,column 형식으로 입력해야 합니다.");
        }
        return values;
    }

    private Position parsePosition(String[] values) {
        try {
            int row = Integer.parseInt(values[0].trim());
            int column = Integer.parseInt(values[1].trim());
            return new Position(row, column);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 숫자로 입력해야 합니다.");
        }
    }
}
