package janggi.view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String POSITION_DELIMITER = ",";
    private static final int POSITION_PART_COUNT = 2;
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    private final Scanner scanner;

    public InputView() {
        this.scanner = new Scanner(System.in);
    }

    public int readBoardInitializeType() {
        return Parser.parse(scanner.nextLine());
    }

    public List<Integer> readPosition() {
        String[] parts = scanner.nextLine()
                .split(POSITION_DELIMITER);

        if (parts.length != POSITION_PART_COUNT) {
            throw new IllegalArgumentException("위치는 '행,열' 형식으로 입력하세요. (예: 3,5)");
        }

        return List.of(
                Parser.parse(parts[ROW_INDEX].trim()),
                Parser.parse(parts[COLUMN_INDEX].trim())
        );
    }

    public int readGameSelection() {
        return Parser.parse(scanner.nextLine());
    }

    public String readGameName() {
        return scanner.nextLine();
    }
}
