package util;

import java.util.Arrays;
import java.util.List;

public final class InputParser {
    public static List<Integer> splitBy(String delimiter, String input) {
        return Arrays.stream(input.split(delimiter))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
    }
}
