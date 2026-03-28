package janggi.domain;

import java.util.Map;

public enum Side {
    CHO("초"),
    HAN("한"),
    EMPTY("없음");

    private static final String INVALID_OPPOSITE_SIDE = "반대 진영이 없습니다.";

    private static final Map<Side, Side> oppositeSide = Map.of(
            Side.CHO, Side.HAN,
            Side.HAN, Side.CHO
    );

    private final String name;

    Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Side getOppositeSide() {
        Side side = oppositeSide.get(this);

        if(side == null) {
            throw new IllegalArgumentException(INVALID_OPPOSITE_SIDE);
        }

        return oppositeSide.get(this);
    }
}
