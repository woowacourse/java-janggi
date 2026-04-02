package view;

import domain.Position;
import domain.player.Name;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputParser {
    private static final Pattern COMMA_SEPARATED_COORDINATES = Pattern.compile("^\\s*\\d+\\s*,\\s*\\d+\\s*$");
    private static final String DELIMITER = ",";

    public static Name parseName(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }
        return new Name(input.strip());
    }

    public static Position parsePosition(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }

        if (!COMMA_SEPARATED_COORDINATES.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. (예: 0,3)");
        }

        return getPosition(input);
    }

    private static Position getPosition(String input) {
        List<Integer> coordinate = Arrays.stream(input.split(DELIMITER))
                .map(String::strip)
                .map(Integer::parseInt)
                .toList();

        return Position.of(coordinate.get(0), coordinate.get(1));
    }
}
