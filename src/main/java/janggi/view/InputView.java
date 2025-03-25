package janggi.view;

import janggi.coordinate.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final int POSITION_START_INDEX = 5;
    private static final int POSITION_X_INDEX = 0;
    private static final int POSITION_Y_INDEX = 1;
    private static final Scanner sc = new Scanner(System.in);

    public static List<Position> readPositions() {
        System.out.println("move <scrPosition> <destPosition> 형식으로 입력해주세요");
        System.out.println("ex) move 일사 십구 => (1, 4) -> (10, 9)");
        final String input = sc.nextLine();

        validateCommand(input);

        final String parsedInput = input.substring(POSITION_START_INDEX);
        final String[] positionTexts = parsedInput.split(" ");
        final List<Position> positions = new ArrayList<>();
        for (final String positionText : positionTexts) {
            final int x = NumberFormat.findNumber(positionText.charAt(POSITION_X_INDEX) + "");
            final int y = NumberFormat.findNumber(positionText.charAt(POSITION_Y_INDEX) + "");
            positions.add(new Position(x, y));
        }
        return positions;
    }

    private static void validateCommand(final String input) {
        if (!input.startsWith("move ")) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다.");
        }
    }
}
