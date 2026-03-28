package view;

import domain.coordinate.Position;
import domain.Side;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import view.message.SideView;

public class InputView {

    private static final String COMMA_DELIMITER = ",";

    private static final String REQUEST_MOVING_START_PIECE_POSITION = "\n%s 턴! 이동할 기물의 좌표를 입력해주세요. (e.g. 2,3)\n";
    private static final String REQUEST_PIECE_DESTINATION = "\n움직일 좌표의 번호를 선택해주세요.";

    private final Scanner sc;

    public InputView(Scanner sc) {
        this.sc = sc;
    }

    public Position requestStartPiecePosition(Side side) {
        try {
            System.out.printf(REQUEST_MOVING_START_PIECE_POSITION, SideView.from(side));
            List<String> strings = splitCoordinate(userInput());
            int col = Integer.parseInt(strings.get(0));
            int row = Integer.parseInt(strings.get(1));

            return new Position(col, row);
        } catch (PatternSyntaxException | NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다. 재입력 해주세요.");
        }
    }

    public int requestPieceDestination() {
        try {
            System.out.println(REQUEST_PIECE_DESTINATION);
            return Integer.parseInt(userInput());
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
