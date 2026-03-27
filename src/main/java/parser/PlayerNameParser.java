package parser;

import java.util.Arrays;
import java.util.List;

public class PlayerNameParser {

    private static final String NAME_SEPARATOR = ",";
    private static final String WHITESPACE = " ";

    private PlayerNameParser() {
    }

    public static List<String> splitNames(String names) {
        List<String> name = Arrays.asList(
                names.replace(WHITESPACE, "").split(NAME_SEPARATOR)
        );

        return name;
    }
}
