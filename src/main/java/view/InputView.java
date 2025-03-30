package view;

import piece.Country;
import position.LineDirection;
import position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    private static final Map<String, LineDirection> LINE_DIRECTION_FORMAT = Map.of(
            "위", LineDirection.UP,
            "아래", LineDirection.DOWN
    );

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

    public static LineSettingDto readLineSettingByCountry() {
        System.out.println("한 나라가 어느 위치에 배정받을 지를 정해주세요. (위, 아래)");
        System.out.println("ex) 위");
        String input = sc.nextLine();
        LineDirection lineDirection = getLineDirectionByInput(input);
        return new LineSettingDto(Country.HAN, lineDirection);
    }

    private static LineDirection getLineDirectionByInput(String input) {
        if (!LINE_DIRECTION_FORMAT.containsKey(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다: " + input);
        }
        return LINE_DIRECTION_FORMAT.get(input);
    }
}
