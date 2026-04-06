package janggi.util;

import java.util.Arrays;
import java.util.List;

public class DelimiterParser {

    private static final String DELIMITER = ",";

    public static List<String> parse(String input) {
        return Arrays.stream(input.split(DELIMITER))
            .map(String::trim)
            .toList();
    }
}
