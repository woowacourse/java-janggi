package view.parser;

import domain.common.Position;
import domain.board.Formation;
import domain.player.Name;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputParser {
    private static final Pattern COORDINATE_CSV_PATTERN = Pattern.compile(" *\\d+ *, *\\d+ *");

    public static Name parseName(String input) {
        validateNullOrBlank(input);
        return new Name(input.strip());
    }

    public static Position parsePosition(String input) {
        validateNullOrBlank(input);
        validateInputFormat(input);

        return getPosition(input);
   }

    public static Formation parseFormation(String input) {
        validateNullOrBlank(input);
        return FormationCommand.from(input).toFormation();
    }

    public static GameStartCommand parseGameStartCommand(String input) {
        validateNullOrBlank(input);
        return GameStartCommand.from(input);
    }

    public static int parseMenuNumber(String input, int min, int max) {
        validateNullOrBlank(input);
        validateRange(min, max);

        int number;
        try {
            number = Integer.parseInt(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해야 합니다.");
        }

        if (number < min || number > max) {
            throw new IllegalArgumentException("범위 내 번호를 입력하세요.");
        }
        return number;
    }

    private static Position getPosition(String input) {
        List<Integer> coordinate = Arrays.stream(input.strip().split(","))
                .map(String::strip)
                .map(Integer::parseInt)
                .toList();

        return Position.of(coordinate.get(0), coordinate.get(1));
    }

    private static void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }
    }

    private static void validateInputFormat(String input) {
        if (!COORDINATE_CSV_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("질못된 입력 형식입니다.");
        }
    }

    private static void validateRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("범위가 올바르지 않습니다.");
        }
    }
}
