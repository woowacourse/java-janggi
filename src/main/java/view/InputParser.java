package view;

import java.util.Arrays;
import java.util.List;

public class InputParser {
    public static List<String> parseTableSetting(String input) {
        input = input.replace(" ", "");

        return Arrays.stream(input.split("")).toList();
    }
}
