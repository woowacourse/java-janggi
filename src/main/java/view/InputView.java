package view;

import piece.Country;
import position.LineDirection;
import position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    private static final Map<String, LineDirection> LINE_DIRECTION_FORMAT = Map.of(
            "위", LineDirection.UP,
            "아래", LineDirection.DOWN
    );

    private static final Map<String, Boolean> INTENT_FORMAT = Map.of(
            "y", Boolean.TRUE,
            "n", Boolean.FALSE
    );

    private static final Map<Boolean, Consumer<Void>> INTENT_ACTIONS = Map.of(
            Boolean.TRUE, (v) -> System.out.println("데이터에 저장된 정보를 불러옵니다."),
            Boolean.FALSE, (v) -> System.out.println("게임을 다시 시작합니다.")
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
        LineDirection lineDirection = getInputByPattern(input, LINE_DIRECTION_FORMAT);
        return new LineSettingDto(Country.HAN, lineDirection);
    }

    public static boolean readClientIntent() {
        System.out.println("데이터베이스에 저장된 정보가 존재합니다.");
        System.out.println("데이터 베이스에 저장된 정보를 불러올까요? (y, n)");

        String input = sc.nextLine();
        Boolean intent = getInputByPattern(input, INTENT_FORMAT);
        INTENT_ACTIONS.get(intent);
        return intent;
    }

    private static <T> T getInputByPattern(String input, Map<String, T> patternFormat) {
        if (!patternFormat.containsKey(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다: " + input);
        }
        return patternFormat.get(input);
    }
}
