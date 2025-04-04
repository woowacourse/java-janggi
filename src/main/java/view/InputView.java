package view;

import domain.piece.Country;
import domain.position.LineDirection;
import domain.position.Position;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        return retryUntilValid("move <scrPosition> <destPosition> 형식으로 입력해주세요\nex) move 일사 이사 => (1, 4) -> (2, 4)", () -> {
            System.out.print("> ");
            final String input = sc.nextLine();

            exitCheck(input);

            if (!input.startsWith("move ")) {
                throw new IllegalArgumentException("잘못된 형식의 입력입니다. 'move'로 시작해야 합니다.");
            }

            final String parsedInput = input.substring(5);
            final String[] positionTexts = parsedInput.split(" ", -1);
            if (positionTexts.length != 2) {
                throw new IllegalArgumentException("출발지와 도착지 좌표를 모두 입력해주세요.");
            }

            final List<Position> positions = new ArrayList<>();
            for (final String positionText : positionTexts) {
                if (positionText.length() != 2) {
                    throw new IllegalArgumentException("좌표 형식이 잘못되었습니다: " + positionText);
                }
                final int x = NumberFormat.findNumber(positionText.charAt(0) + "");
                final int y = NumberFormat.findNumber(positionText.charAt(1) + "");
                positions.add(new Position(x, y));
            }
            return positions;
        });
    }

    private static void exitCheck(String input) {
        if (input.equals("exit")) {
            System.out.println("게임이 종료됩니다.");
            System.exit(0);
        }
    }

    public static LineSettingDto readLineSettingByCountry() {
        return retryUntilValid("한 나라가 어느 위치에 배정받을 지를 정해주세요. (위, 아래)\nex) 위", () -> {
            System.out.print("> ");
            String input = sc.nextLine();
            LineDirection lineDirection = getInputByPattern(input, LINE_DIRECTION_FORMAT);
            return new LineSettingDto(Country.HAN, lineDirection);
        });
    }

    public static boolean readClientIntent() {
        return retryUntilValid("데이터베이스에 저장된 정보가 존재합니다.\n데이터 베이스에 저장된 정보를 불러올까요? (y, n)", () -> {
            System.out.print("> ");
            String input = sc.nextLine();
            Boolean intent = getInputByPattern(input, INTENT_FORMAT);
            INTENT_ACTIONS.get(intent).accept(null);
            return intent;
        });
    }

    private static <T> T getInputByPattern(String input, Map<String, T> patternFormat) {
        exitCheck(input);
        if (!patternFormat.containsKey(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다: " + input + " (가능한 입력: " + patternFormat.keySet() + ")");
        }
        return patternFormat.get(input);
    }

    private static <T> T retryUntilValid(String message, Supplier<T> action) {
        while (true) {
            try {
                System.out.println(message);
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("다시 입력해주세요.\n");
            }
        }
    }
}
