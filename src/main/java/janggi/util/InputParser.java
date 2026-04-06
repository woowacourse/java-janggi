package janggi.util;

import static janggi.util.HorseElephantPositionMapper.EHHE_ORDINAL;
import static janggi.util.HorseElephantPositionMapper.HEHE_ORDINAL;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class InputParser {

    public static long parseGameId(String input) {
        return parseNumber(input, Long::parseLong);
    }

    public static int parseHorseElephantPositionOrdinal(String input) {
        int ordinal = parseNumber(input, Integer::parseInt);
        validateRange(ordinal);
        return ordinal;
    }

    public static List<Integer> parsePosition(String input) {
        String[] split = input.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException("콤마로 구분된 두 개의 숫자를 올바르게 입력해주세요.");
        }
        return Arrays.stream(split)
                .map(value -> parseNumber(value, Integer::parseInt))
                .toList();
    }

    private static <T> T parseNumber(String input, Function<String, T> parser) {
        try {
            return parser.apply(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자로 입력해주세요.");
        }
    }


    private static void validateRange(int ordinal) {
        if (ordinal < HEHE_ORDINAL || ordinal > EHHE_ORDINAL) {
            throw new IllegalArgumentException("1, 2, 3, 4 중 하나의 숫자를 입력해주세요.");
        }
    }

}
