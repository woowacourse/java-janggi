package janggi.view;

import janggi.view.mapping.BoardType;
import janggi.view.util.Parser;
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

    public BoardType readBoardInitializeType() {
        return BoardType.of(Parser.parseInt(scanner.nextLine()));
    }

    public List<Integer> readPosition() {
        String[] parts = scanner.nextLine()
                .split(POSITION_DELIMITER);

        if (parts.length != POSITION_PART_COUNT) {
            throw new IllegalArgumentException("위치는 '행,열' 형식으로 입력하세요. (예: 3,5)");
        }

        return List.of(
                Parser.parseInt(parts[ROW_INDEX].trim()),
                Parser.parseInt(parts[COLUMN_INDEX].trim())
        );
    }

    public boolean readYesOrNo() {
        String input = scanner.nextLine();

        if ("y".equals(input)) {
            return true;
        }

        if ("n".equals(input)) {
            return false;
        }

        throw new IllegalArgumentException("유효하지 않은 값이 입력됐스니다.");
    }

    public Long readGameOption() {
        return Parser.parseLong(scanner.nextLine());
    }
}
