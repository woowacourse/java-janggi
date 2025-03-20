package view;

import board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    public static List<Position> readPositions() {
        System.out.println("move <scrPosition> <destPosition> 형식으로 입력해주세요");
        System.out.println("ex) move 일사 십구 => (1, 4) -> (10, 9)");
        final String input = sc.nextLine();

        if (!input.startsWith("move ")) {
            throw new IllegalArgumentException("잘못된 형식의 입력입니다.");
        }

        final String parsedInput = input.substring(5);
        final String[] positionTexts = parsedInput.split(" ", -1);
        final List<Position> positions = new ArrayList<>();
        for (final String positionText : positionTexts) {
            final int x = NumberFormat.findNumber(positionText.charAt(0) + "");
            final int y = NumberFormat.findNumber(positionText.charAt(1) + "");
            positions.add(new Position(x, y));
        }
        return positions;
    }
}
