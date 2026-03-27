package view;

import java.util.Arrays;
import java.util.List;

public class InputParser {
    private static final int POSITION_COUNT = 2;

    private static final String INVALID_POSITION_FORMAT = "[ERROR] 잘못된 좌표 형식입니다.";
    private static final String INVALID_POSITION_COUNT = "[ERROR] 좌표는 2개만 입력해 주세요.";

    public static String parseTableSetting(String input) {
        input = input.replace(" ", "");
        return input;
    }

    public static List<Integer> parsePosition(String input) {
        input = input.replace(" ", "");
        List<String> inputs = Arrays.asList(input.split(","));
        List<Integer> numbers = parseNumbers(inputs);
        validateCount(numbers);
        return numbers;
    }

    public static List<Integer> parseNumbers(List<String> inputs) {
        try {
            return inputs.stream()
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(INVALID_POSITION_FORMAT);
        }
    }

    private static void validateCount(List<Integer> inputs) {
        if (inputs.size() != POSITION_COUNT) {
            throw new IllegalArgumentException(INVALID_POSITION_COUNT);
        }
    }
}
