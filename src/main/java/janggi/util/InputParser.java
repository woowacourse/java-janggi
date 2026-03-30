package janggi.util;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    private static final String DELIMITER = ",";

    public static List<String> splitByDelimiter(String input) {
        validateInput(input);
        List<String> inputs = Arrays.stream(input.split(DELIMITER))
            .map(String::trim)
            .filter(name -> !name.isBlank())
            .toList();

        validateInputs(inputs);
        return inputs;
    }

    private static void validateInput(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 좌표값을 입력해주세요.");
        }
    }

    private static void validateInputs(List<String> inputs) {
        if (inputs.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 좌표는 x,y 형태로 입력해야합니다.");
        }
    }

}
