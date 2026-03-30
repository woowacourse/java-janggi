package domain;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class InputParser {
    public static final Pattern POSITION_PATTERN = Pattern.compile(" *\\( *\\d+ *, *\\d+ *\\) *");

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
        
        if (!POSITION_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("질못된 입력 형식입니다.");
        }

        return getPosition(input);
   }

    private static Position getPosition(String input) {
        List<Integer> coordinate = Arrays.stream(input.strip().substring(1, input.length() - 1).split(","))
                .map(String::strip)
                .map(Integer::parseInt)
                .toList();

        return Position.of(coordinate.get(0), coordinate.get(1));
    }
}
