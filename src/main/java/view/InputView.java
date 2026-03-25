package view;

import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String COMMA_DELIMITER = ",";

    private static final String REQUEST_MOVING_START_PIECE_POSITION = "\n이동할 기물의 좌표를 입력해주세요. (e.g. 2,3)";

    private final Scanner sc = new Scanner(System.in);

    public Position requestPiecePosition() {
        try {
            System.out.println(REQUEST_MOVING_START_PIECE_POSITION);
            List<String> strings = splitCoordinate(userInput());
            int col = Integer.parseInt(strings.get(0));
            int row = Integer.parseInt(strings.get(1));

            return new Position(col, row);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    private List<String> splitCoordinate(String userInput) {
        return Arrays.stream(userInput.split(COMMA_DELIMITER)).toList();
    }

    private String userInput() {
        return sc.nextLine();
    }
}
