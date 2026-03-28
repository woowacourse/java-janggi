package janggi.util;

import janggi.domain.game.Side;
import java.util.Map;

public class SideDisplayNameMapper {

    private static final Map<Side, String> NAMES = Map.of(
            Side.CHO, "초",
            Side.HAN, "한"
    );

    public static String toDisplayName(Side side) {
        return NAMES.get(side);
    }
}
