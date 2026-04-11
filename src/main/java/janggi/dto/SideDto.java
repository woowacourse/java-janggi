package janggi.dto;

import janggi.domain.Side;
import java.util.Map;

public record SideDto(String name) {
    private static final Map<Side, String> SIDE_NAMES = Map.of(
            Side.CHO, "초",
            Side.HAN, "한"
    );

    public static SideDto from(Side side) {
        return new SideDto(SIDE_NAMES.get(side));
    }
}
