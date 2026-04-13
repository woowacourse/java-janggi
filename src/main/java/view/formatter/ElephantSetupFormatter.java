package view.formatter;

import java.util.Map;

public final class ElephantSetupFormatter {

    private static final String INNER_ELEPHANT_SETUP_DISPLAY_NAME = "마상상마(馬象象馬)";
    private static final String OUTER_ELEPHANT_SETUP_DISPLAY_NAME = "상마마상(象馬馬象)";
    private static final String RIGHT_ELEPHANT_SETUP_DISPLAY_NAME = "마상마상(馬象馬象)";
    private static final String LEFT_ELEPHANT_SETUP_DISPLAY_NAME = "상마상마(象馬象馬)";

    private static final String INNER_ELEPHANT_SETUP = "INNER";
    private static final String OUTER_ELEPHANT_SETUP = "OUTER";
    private static final String RIGHT_ELEPHANT_SETUP = "RIGHT";
    private static final String LEFT_ELEPHANT_SETUP = "LEFT";

    private static final Map<String, String> ELEPHANT_SETUP_DISPLAY_NAMES = Map.of(
            INNER_ELEPHANT_SETUP, INNER_ELEPHANT_SETUP_DISPLAY_NAME,
            OUTER_ELEPHANT_SETUP, OUTER_ELEPHANT_SETUP_DISPLAY_NAME,
            RIGHT_ELEPHANT_SETUP, RIGHT_ELEPHANT_SETUP_DISPLAY_NAME,
            LEFT_ELEPHANT_SETUP, LEFT_ELEPHANT_SETUP_DISPLAY_NAME
    );

    private ElephantSetupFormatter() {
    }

    public static String format(final String elephantSetup) {
        if (!ELEPHANT_SETUP_DISPLAY_NAMES.containsKey(elephantSetup)) {
            throw new IllegalArgumentException("정의되지 않은 상차림입니다. elephantSetup: " + elephantSetup);
        }
        return ELEPHANT_SETUP_DISPLAY_NAMES.get(elephantSetup);
    }
}
