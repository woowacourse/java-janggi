package janggi.view.resolver;

import janggi.domain.Side;


public class SideViewResolver {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private SideViewResolver() {
    }

    public static String toDisplayName(Side side) {
        String name = switch (side) {
            case CHO -> "초";
            case HAN -> "한";
        };

        if (side == Side.HAN) {
            return ANSI_RED + name + ANSI_RESET;
        }
        return ANSI_BLUE + name + ANSI_RESET;
    }
}
