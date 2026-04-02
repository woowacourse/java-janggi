package parser;

import java.util.Arrays;
import java.util.List;

public class PlayerNameParser {

    private static final String NAME_SEPARATOR = ",";
    private static final String WHITESPACE = " ";
    private static final int PLAYER_NAME_COUNT = 2;

    private PlayerNameParser() {
    }

    public static List<String> splitNames(String names) {
        List<String> name = Arrays.asList(
                names.replace(WHITESPACE, "").split(NAME_SEPARATOR)
        );

        validateNameCount(name);

        return name;
    }

    private static void validateNameCount(List<String> names) {
        if (names.size() != PLAYER_NAME_COUNT) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 2명을 입력해야 합니다.");
        }
    }
}
